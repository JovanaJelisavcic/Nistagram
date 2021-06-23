package com.XMLiWS.microservices.feedservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public Feed getUnregisteredFeed() {
		ArrayList<Post> feed = repository.findForUnregistered(new Date());
		if(feed.isEmpty()) {
			throw new RuntimeException("Unable to find data");
		}
		

	
		return new Feed(1000L, false, feed);
	}
	
	@GetMapping("/feed/registered/{userid}")
	public Feed getRegisteredFeed(@PathVariable("userid") long userid) {
		List<Long> followings = (List<Long>) proxy.usersFollowingIds(userid);
		logger.info(followings.toString());
		if(followings.isEmpty()) {
			throw new RuntimeException("No followings");
		}
		
		ArrayList<Post> feed = repository.findByuserIDInAndPublishedLessThanEqual(followings,new Date());
		logger.info(feed.toString());
		if(feed.isEmpty()) {
			throw new RuntimeException("Unable to find data");
		}
		    feed.sort((o1,o2) -> o1.getPublished().compareTo(o2.getPublished()));
			return new Feed(1001L, true, feed);
		
	}
}

