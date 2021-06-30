package com.XMLiWS.microservices.userservice.controller;



import java.net.URI;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.XMLiWS.microservices.userservice.bean.User;
import com.XMLiWS.microservices.userservice.repository.FollowRepository;
import com.XMLiWS.microservices.userservice.repository.UserRepository;

@RestController
public class UserController {

		Logger logger = LoggerFactory.getLogger(UserController.class);
		
		@Autowired
		UserRepository userRepo;
		@Autowired
		FollowRepository followRepo;
		
		@GetMapping("/user/{id}")
		public ResponseEntity<User> getUser(@PathVariable String id) {
			User user = userRepo.findByUserId(Long.parseLong(id));
			if(user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		
		@PostMapping("/user")
		public ResponseEntity<Object> createUser(@RequestBody User user) {
			User savedUser = userRepo.save(user);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(savedUser.getUserId()).toUri();

			return ResponseEntity.created(location).build();

		}
		
		@PutMapping("/user")
		public ResponseEntity<Object> updateUser(@RequestBody User user) {

			Optional<User> userOptional = userRepo.findById(user.getUserId());
			if (!userOptional.isPresent()) {
				return ResponseEntity.notFound().build();
			}
			userRepo.save(user);
			return ResponseEntity.ok().build();
		}
		
		
	
		
		@GetMapping("/user/{id}/privacy")
		public ResponseEntity<Boolean> getPrivacy(@PathVariable long id) {

			Optional<User> userOptional = userRepo.findById(id);
			if(userOptional.isPresent()) {
			return new ResponseEntity<Boolean>(userOptional.get().isPrivacy(), HttpStatus.OK);
			}else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	
		
		
		
}
