package com.XMLiWS.microservices.feedservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.XMLiWS.microservices.feedservice.bean.Post;
import com.XMLiWS.microservices.feedservice.proxy.UserProxy;
import com.XMLiWS.microservices.feedservice.repository.PostRepository;

@RestController
public class FeedController {
	
	Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@Autowired
	private PostRepository repository;

	@Autowired
	private UserProxy proxy;

	
	@GetMapping("/feed/unregistered")
	public ResponseEntity<List<Post>> getUnregisteredFeed() {
		ArrayList<Post> feed = repository.findForUnregistered(new Date());
		if(feed.isEmpty()) {
			 return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		
		logger.info(feed.get(0).getHashtags().toString());
	
		return new ResponseEntity<List<Post>>(feed, HttpStatus.OK);
	}
	
	@GetMapping("/feed/registered/{userid}")
	public ResponseEntity<List<Post>> getRegisteredFeed(@PathVariable("userid") long userid) {
		List<Long> followings = (List<Long>) proxy.usersFollowingIds(userid).getBody();
		if(followings.isEmpty()) {
			return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<Post> feed = repository.findByuserIDInAndPublishedLessThanEqual(followings,new Date());
		if(feed.isEmpty()) {
			 return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		    feed.sort((o1,o2) -> o1.getPublished().compareTo(o2.getPublished()));
		    return new ResponseEntity<List<Post>>(feed, HttpStatus.OK);
		
	}
}

