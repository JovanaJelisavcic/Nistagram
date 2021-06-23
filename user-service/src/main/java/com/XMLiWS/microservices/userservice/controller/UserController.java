package com.XMLiWS.microservices.userservice.controller;



import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.XMLiWS.microservices.userservice.bean.Followers;
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
		public User getUser(@PathVariable String id) {
			User user = userRepo.findByUserId(Long.parseLong(id));
			if(user == null) {
				throw new RuntimeException("Unable to find user");
			}
			return user;
		}
		
		@PostMapping("/user")
		public ResponseEntity<Object> createUser(@RequestBody User user) {
			User savedUser = userRepo.save(user);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(savedUser.getUserId()).toUri();

			return ResponseEntity.created(location).build();

		}
		
		@PutMapping("/user/{id}")
		public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable long id) {

			Optional<User> userOptional = userRepo.findById(id);

			if (!userOptional.isPresent())
				return ResponseEntity.notFound().build();

			userRepo.save(user);

			return ResponseEntity.noContent().build();
		}
		
		
		@GetMapping("/user/following/{id}")
		public List<Long> usersFollowingIds(@PathVariable long id) {

			Optional<User> userOptional = userRepo.findById(id);
			logger.info("useroptional"+userOptional);

			User user = userOptional.get();
			logger.info("yusr" +user);
			List<Followers> following = user.getFollowing();
			List<Long> ids =new ArrayList<Long>();
			for (Followers pair : following) 
			{ 
				logger.info("pair" +pair);
			   ids.add(pair.getTo().getUserId());
			   logger.info("the id" +pair.getTo().getUserId());
			}
			if(ids.isEmpty()) {
				throw new RuntimeException("No followings");
			}
			return ids;
		}
		
		@GetMapping("/user/{id}/privacy")
		public boolean getPrivacy(@PathVariable long id) {

			Optional<User> userOptional = userRepo.findById(id);
			return userOptional.get().isPrivacy();
		}
		
		
		@PostMapping("/followers/{fromId}/{toId}")
		public ResponseEntity<Object> addFollowing(@PathVariable long fromId,@PathVariable long toId) {
			
			Followers savedFollow = followRepo.save(new Followers(userRepo.findByUserId(fromId), userRepo.findByUserId(toId)));

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(savedFollow.getFollowersId()).toUri();

			return ResponseEntity.created(location).build();

		}
		
		
		
}
