package com.XMLiWS.microservices.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.XMLiWS.microservices.authservice.model.JWTRequest;
import com.XMLiWS.microservices.authservice.model.JWTResponse;
import com.XMLiWS.microservices.authservice.model.UserDTO;
import com.XMLiWS.microservices.authservice.service.UserService;
import com.XMLiWS.microservices.authservice.util.JWTUtility;

@RestController
public class UserController {

		@Autowired
		private JWTUtility jwtUtility;
		@Autowired
		private AuthenticationManager authenticationManager;
		@Autowired
		private UserService userService;
		
		@GetMapping("/")
		public String user() {
			return "user";
		}
		
		@PostMapping("/authenticate")
		public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest ) throws Exception {
			try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
			} catch(BadCredentialsException e) {
				throw new Exception("Invalid_credentials", e);
			}
			
			final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
			
			final String token = jwtUtility.generateToken(userDetails);
			
			return new JWTResponse(token);
		}
		
		@PostMapping("/create")
		public JWTResponse create(@RequestBody UserDTO userDTO ) throws Exception {
			
			
			
			final UserDetails userDetails = userService.registerNewUserAccount(userDTO);
			
			final String token = jwtUtility.generateToken(userDetails);
			
			return new JWTResponse(token);
		}
}
