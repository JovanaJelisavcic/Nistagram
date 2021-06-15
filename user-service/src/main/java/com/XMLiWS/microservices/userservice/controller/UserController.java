package com.XMLiWS.microservices.userservice.controller;



import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.XMLiWS.microservices.userservice.bean.User;
import com.XMLiWS.microservices.userservice.repository.UserRepository;

@RestController
public class UserController {

		@Autowired
		UserRepository userRepo;
		
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
		
}
