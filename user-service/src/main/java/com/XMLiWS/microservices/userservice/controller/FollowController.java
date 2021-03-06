package com.XMLiWS.microservices.userservice.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.XMLiWS.microservices.userservice.bean.Followers;
import com.XMLiWS.microservices.userservice.bean.User;
import com.XMLiWS.microservices.userservice.repository.FollowRepository;
import com.XMLiWS.microservices.userservice.repository.UserRepository;
import com.XMLiWS.microservices.userservice.util.TokenUtil;
import com.XMLiWS.microservices.userservice.view.View;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/follows")
public class FollowController {

	Logger logger = LoggerFactory.getLogger(FollowController.class);

	@Autowired
	UserRepository userRepo;
	@Autowired
	FollowRepository followRepo;
	@Autowired
	private TokenUtil tokenUtil;
	

	@GetMapping("/public/following/{username}")
	@JsonView(View.Detailed.class)
	public ResponseEntity<List<String>> usersFollowingIds(@PathVariable String username) {

		Optional<User> userOptional = userRepo.findByUsername(username);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			List<Followers> following = user.getFollowing();
			List<String> ids = new ArrayList<String>();
			for (Followers pair : following) {
				if (pair.isAccepted()) {
					ids.add(pair.getTo().getUsername());
				}
			}
			if (ids.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<String>>(ids, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/public/followers/{username}")
	@JsonView(View.Detailed.class)
	public ResponseEntity<List<String>> usersFollowers(@PathVariable String username) {

		Optional<User> userOptional = userRepo.findByUsername(username);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			List<Followers> following = user.getFollowers();
			List<String> ids = new ArrayList<String>();
			for (Followers pair : following) {
				if (pair.isAccepted()) {
					ids.add(pair.getFrom().getUsername());
				}
			}
			if (ids.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<String>>(ids, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/followPublic/{user}")
	@JsonView(View.Simple.class)
	public ResponseEntity<Object> addFollowingPublic(@RequestHeader("Authorization") String token, @PathVariable String user) {
		String username = tokenUtil.extractIdentity(token);
		Optional<User> fromUser = userRepo.findByUsername(username);
		Optional<User> toUser = userRepo.findByUsername(user);
		if (!fromUser.isPresent() || !toUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else if (followRepo.findFollowings(fromUser.get().getUserId(), toUser.get().getUserId()) == null && !toUser.get().isPrivacy()) {
			Followers follow = new Followers(fromUser.get(), toUser.get());
			follow.setAccepted(true);
			followRepo.save(follow);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(follow.getFollowersId()).toUri();

			return ResponseEntity.created(location).build();
		} else
			return new ResponseEntity<>(HttpStatus.CONFLICT);

	}

	@PostMapping("/followRequest/{user}")
	@JsonView(View.Detailed.class)
	public ResponseEntity<Object> addFollowingRequest(@RequestHeader("Authorization") String token, @PathVariable String user) {
		String username = tokenUtil.extractIdentity(token);
		Optional<User> fromUser = userRepo.findByUsername(username);
		Optional<User> toUser = userRepo.findByUsername(user);
		if (!fromUser.isPresent() || !toUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else if (followRepo.findFollowings(fromUser.get().getUserId(),  toUser.get().getUserId()) == null && toUser.get().isPrivacy() ) {
			Followers follow = new Followers(fromUser.get(), toUser.get());
			follow.setAccepted(false);
			followRepo.save(follow);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(follow.getFollowersId()).toUri();

			return ResponseEntity.created(location).build();
		} else
			return new ResponseEntity<>(HttpStatus.CONFLICT);

	}

	@PutMapping("/followAccept/{user}")
	@JsonView(View.Detailed.class)
	public ResponseEntity<Object> addFollowingAccept(@RequestHeader("Authorization") String token, @PathVariable String user) {
		
		String username = tokenUtil.extractIdentity(token);
		Optional<User> toUser = userRepo.findByUsername(username);
		Optional<User> fromUser = userRepo.findByUsername(user);
		if (!fromUser.isPresent() || !toUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {		
		Followers follow = followRepo.findFollowings(toUser.get().getUserId(), toUser.get().getUserId());
		if (follow!=null) {
			follow.setAccepted(true);
			followRepo.save(follow);
			return ResponseEntity.ok().build();
			}else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
			
	}
	
	@GetMapping("/followRequests")
	@JsonView(View.Detailed.class)
	public ResponseEntity<?> followRequests(@RequestHeader("Authorization") String token) {
		String username = tokenUtil.extractIdentity(token);
		User user = userRepo.findByUsername(username).get();
		List<Followers> followers = user.getFollowers();
		followers.removeIf(f->f.isAccepted());
		List<User> users = new ArrayList<>();
		for(Followers f : followers) {
			users.add(f.getFrom());
		}
		if(users.isEmpty())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	

}
