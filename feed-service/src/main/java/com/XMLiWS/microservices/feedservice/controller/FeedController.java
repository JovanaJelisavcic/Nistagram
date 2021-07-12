package com.XMLiWS.microservices.feedservice.controller;

import java.time.LocalDateTime;
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

import com.XMLiWS.microservices.feedservice.bean.Feed;
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
	public ResponseEntity<Feed> getUnregisteredFeed() {
		ArrayList<Post> posts = repository.findForUnregisteredPosts(LocalDateTime.now());
		ArrayList<Post> stories = repository.findForUnregisteredStories(LocalDateTime.now(),LocalDateTime.now().minusHours(24));
		logger.info(LocalDateTime.now()+ " " + LocalDateTime.now().minusHours(24) );
		if(posts.isEmpty() && stories.isEmpty() ) {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Feed feed = new Feed();
		feed.setPosts(posts);
		feed.setStories(stories);
		return new ResponseEntity<Feed>(feed, HttpStatus.OK);
	}
	
	@GetMapping("/feed/registered/{userid}")
	public ResponseEntity<Feed> getRegisteredFeed(@PathVariable("userid") long userid) {
		List<Long> followings = (List<Long>) proxy.usersFollowingIds(userid).getBody();
		if(followings.isEmpty()) {
			return new ResponseEntity<Feed>(HttpStatus.NOT_FOUND);
		}
		
		ArrayList<Post> posts = repository.findPostsForRegistered(followings,LocalDateTime.now());
		ArrayList<Post> stories = repository.findStoriesForRegistered(followings,LocalDateTime.now(),LocalDateTime.now().minusHours(24));
		if(posts.isEmpty() && stories.isEmpty()) {
			 return new ResponseEntity<Feed>(HttpStatus.NOT_FOUND);
		}
		logger.info(stories.toString());
			Feed feed = new Feed();
			feed.setPosts(posts);
			feed.setStories(stories);
		    return new ResponseEntity<Feed>(feed, HttpStatus.OK);
		
	}
	
	
}

