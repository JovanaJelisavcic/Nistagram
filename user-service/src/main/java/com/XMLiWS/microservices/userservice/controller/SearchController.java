package com.XMLiWS.microservices.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.XMLiWS.microservices.userservice.bean.User;
import com.XMLiWS.microservices.userservice.repository.UserRepository;
import com.XMLiWS.microservices.userservice.view.View;
import com.fasterxml.jackson.annotation.JsonView;



@RestController
public class SearchController {
	
	Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private UserRepository repository;
	
	@GetMapping("/user/searchHash/{hash}")
	@JsonView(View.Simple.class)
	public ResponseEntity<List<User>> searchHashAll(@PathVariable String hash) {
		 StringBuilder sb = new StringBuilder(hash.concat("%"));
		 sb.insert(0,"%");
		List<User> users = repository.findAllByUsernameLikeIgnoreCase(sb.toString());
		
		if(users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/public/user/searchHash/{hash}")
	@JsonView(View.Simple.class)
	public ResponseEntity<List<User>> searchHashPublic(@PathVariable String hash) {
		 StringBuilder sb = new StringBuilder(hash.concat("%"));
		 sb.insert(0,"%");
		List<User> users = repository.findAllByUsernameLikeIgnoreCaseAndPrivacy(sb.toString(),false);
		
		if(users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	
	@GetMapping("/user/searchProfiles/{name}")
	@JsonView(View.Simple.class)
	public ResponseEntity<List<User>> searchProfiles(@PathVariable String name) {
		List<User> users = null;
		if(name.contains(" ")) {
			String[] words = name.split(" ");
			List<String> parms = new ArrayList<>();
			for (String word : words) {
				 StringBuilder sb = new StringBuilder(word.concat("%"));
				 sb.insert(0,"%");
				 parms.add(sb.toString());
			}
	            users = repository.findByNames(parms.get(0), parms.get(1));
		}else {
			 StringBuilder sb = new StringBuilder(name.concat("%"));
				sb.insert(0,"%");
			 users = repository.findByNameLikeIgnoreCaseOrSurnameLikeIgnoreCase(sb.toString(),sb.toString());
		}
		if(users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK); 
	}
	
	
	@GetMapping("/public/user/searchProfiles/{name}")
	@JsonView(View.Simple.class)
	public ResponseEntity<List<User>> searchProfilesPublic(@PathVariable String name) {
		logger.info(name);
		List<User> users = null;
		if(name.contains(" ")) {
			String[] words = name.split(" ");
			List<String> parms = new ArrayList<>();
			for (String word : words) {
				logger.info(word);
				 StringBuilder sb = new StringBuilder(word.concat("%"));
				 sb.insert(0,"%");
				 parms.add(sb.toString());
			}
			logger.info(parms.get(0)+" " + parms.get(1));
	            users = repository.findByNamesPublic(parms.get(0), parms.get(1));
		}else {
			 StringBuilder sb = new StringBuilder(name.concat("%"));
				sb.insert(0,"%");
				logger.info(sb.toString());
			 users = repository.findByNamePublic(sb.toString());
		}
		if(users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK); 
	}
	

	

}
