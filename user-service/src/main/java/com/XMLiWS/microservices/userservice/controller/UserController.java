package com.XMLiWS.microservices.userservice.controller;



import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.XMLiWS.microservices.userservice.bean.Followers;
import com.XMLiWS.microservices.userservice.bean.User;
import com.XMLiWS.microservices.userservice.proxy.AuthProxy;
import com.XMLiWS.microservices.userservice.repository.FollowRepository;
import com.XMLiWS.microservices.userservice.repository.UserRepository;
import com.XMLiWS.microservices.userservice.util.TokenUtil;
import com.XMLiWS.microservices.userservice.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/users")
public class UserController {

		Logger logger = LoggerFactory.getLogger(UserController.class);
		
		@Autowired
		UserRepository userRepo;
		@Autowired
		FollowRepository followRepo;
		@Autowired
		private AuthProxy authProxy;
		@Autowired
		private TokenUtil tokenUtil;
		
		 ObjectMapper mapper = new ObjectMapper();
		
		@GetMapping("/public/{username}")
		public ResponseEntity<String> publicGetUser(@PathVariable String username) throws JsonProcessingException {
			Optional<User> user = userRepo.findByUsername(username);
			if(user.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else if(user.get().isPrivacy()) {
				return new ResponseEntity<String>(mapper.writerWithView(View.Simple.class).writeValueAsString(user.get()),HttpStatus.OK);
			}else 
			return new ResponseEntity<String>(mapper.writerWithView(View.Detailed.class).writeValueAsString(user.get()), HttpStatus.OK);
		}
		

		@GetMapping("/{username}")
		public ResponseEntity<String> getUser(@RequestHeader("Authorization") String token, @PathVariable String username1) throws JsonProcessingException {
			String username = tokenUtil.extractIdentity(token);
			User user = userRepo.findByUsername(username1).get();
			if(user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else if(!user.isPrivacy()) {
				return new ResponseEntity<String>(mapper.writerWithView(View.Detailed.class).writeValueAsString(user), HttpStatus.OK);
				
			}else {
				Followers follow = followRepo.findFollowings(userRepo.findByUsername(username).get().getUserId(), userRepo.findByUsername(username1).get().getUserId());
				if(follow!=null && follow.isAccepted()) {
					return new ResponseEntity<String>(mapper.writerWithView(View.Detailed.class).writeValueAsString(user), HttpStatus.OK);
				}else  return new ResponseEntity<String>(mapper.writerWithView(View.Simple.class).writeValueAsString(user), HttpStatus.OK);
				
			}		
				
			}
			
		@GetMapping("/me")
		@JsonView(View.Detailed.class)
		public User showMe(@RequestHeader("Authorization") String token){
			String username = tokenUtil.extractIdentity(token);
			Optional<User> meUser = userRepo.findByUsername(username);
			return meUser.get();
		}
		
		
		@PostMapping("/public/create")
		@JsonView(View.Detailed.class)
		public ResponseEntity<Object> createUser(@RequestBody User user) {
			logger.info("hit the user"+ user.getUsername());
			if(user.getUserId()!=null) {
				if(userRepo.findByUserId(user.getUserId())!=null)
					return ResponseEntity.status(HttpStatus.CONFLICT).build();
				
			}
			
			
			String token = authProxy.create(user);
			if(!token.isBlank()) {
			userRepo.save(user);

			//URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				//	.buildAndExpand(savedUser.getUserId()).toUri();

			return ResponseEntity.ok(token);
			}else return ResponseEntity.unprocessableEntity().build();

		}
		
		@PutMapping("/update")
		@JsonView(View.Detailed.class)
		public ResponseEntity<Object> updateUser(@RequestHeader("Authorization") String token,@RequestBody User user) {
			
			Optional<User> userOptional = userRepo.findByUsername(user.getUsername());
			if (!userOptional.isPresent()) {
				return ResponseEntity.notFound().build();
			}else if(!tokenUtil.checkIdentity(userOptional.get().getUsername(), token)) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}else {
				user.setUserId(userOptional.get().getUserId());
			userRepo.save(user);
			return ResponseEntity.ok().build();}
		}
		
	
		
		@GetMapping("/public/{username}/privacy")
		@JsonView(View.Detailed.class)
		public ResponseEntity<Boolean> getPrivacy(@PathVariable String username) {

			Optional<User> userOptional = userRepo.findByUsername(username);
			if(userOptional.isPresent()) {
			return new ResponseEntity<Boolean>(userOptional.get().isPrivacy(), HttpStatus.OK);
			}else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
}
