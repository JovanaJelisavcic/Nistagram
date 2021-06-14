package com.XMLiWS.microservices.feedservice.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.XMLiWS.microservices.feedservice.bean.Feed;
import com.XMLiWS.microservices.feedservice.bean.Post;
import com.XMLiWS.microservices.feedservice.repository.PostRepository;

@RestController
public class FeedController {
	
	@Autowired
	private PostRepository repository;
	
	@GetMapping("/feed/unregistered")
	public Feed getUnregisteredFeed() {
		ArrayList<Post> feed = repository.findForUnregistered(new Date());
		if(feed.isEmpty()) {
			throw new RuntimeException("Unable to find data");
		}
		return new Feed(1000L, false, feed );
	}

}
