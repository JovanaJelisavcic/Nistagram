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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.XMLiWS.microservices.userservice.bean.Followers;
import com.XMLiWS.microservices.userservice.bean.User;
import com.XMLiWS.microservices.userservice.repository.FollowRepository;
import com.XMLiWS.microservices.userservice.repository.UserRepository;

@RestController
public class FollowController {

	Logger logger = LoggerFactory.getLogger(FollowController.class);

	@Autowired
	UserRepository userRepo;
	@Autowired
	FollowRepository followRepo;

	@GetMapping("/user/following/{id}")
	public ResponseEntity<List<Long>> usersFollowingIds(@PathVariable long id) {

		Optional<User> userOptional = userRepo.findById(id);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			List<Followers> following = user.getFollowing();
			List<Long> ids = new ArrayList<Long>();
			for (Followers pair : following) {
				if (pair.isAccepted()) {
					ids.add(pair.getTo().getUserId());
				}
			}
			if (ids.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Long>>(ids, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/followPublic/{fromId}/{toId}")
	public ResponseEntity<Object> addFollowingPublic(@PathVariable long fromId, @PathVariable long toId) {

		if (!userRepo.findById(fromId).isPresent() || !userRepo.findById(toId).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else if (followRepo.findFollowings(fromId, toId) == null) {
			Followers follow = new Followers(userRepo.findByUserId(fromId), userRepo.findByUserId(toId));
			follow.setAccepted(true);
			followRepo.save(follow);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(follow.getFollowersId()).toUri();

			return ResponseEntity.created(location).build();
		} else
			return new ResponseEntity<>(HttpStatus.CONFLICT);

	}

	@PutMapping("/followRequest/{fromId}/{toId}")
	public ResponseEntity<Object> addFollowingRequest(@PathVariable long fromId, @PathVariable long toId) {

		if (!userRepo.findById(fromId).isPresent() || !userRepo.findById(toId).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else if (followRepo.findFollowings(fromId, toId) == null) {
			Followers follow = new Followers(userRepo.findByUserId(fromId), userRepo.findByUserId(toId));
			follow.setAccepted(false);
			followRepo.save(follow);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(follow.getFollowersId()).toUri();

			return ResponseEntity.created(location).build();
		} else
			return new ResponseEntity<>(HttpStatus.CONFLICT);

	}

	@PutMapping("/followAccept/{fromId}/{toId}")
	public ResponseEntity<Object> addFollowingAccept(@PathVariable long fromId, @PathVariable long toId) {
		if (followRepo.findFollowings(fromId, toId) != null) {
			Followers follow = followRepo.findFollowings(fromId, toId);
			logger.info(follow + "ovo je followww");
			follow.setAccepted(true);
			followRepo.save(follow);

			return ResponseEntity.ok().build();
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
