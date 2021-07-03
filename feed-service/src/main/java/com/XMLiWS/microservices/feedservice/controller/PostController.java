package com.XMLiWS.microservices.feedservice.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.XMLiWS.microservices.feedservice.bean.Post;
import com.XMLiWS.microservices.feedservice.proxy.UserProxy;
import com.XMLiWS.microservices.feedservice.repository.PostRepository;



@RestController
public class PostController {
	
	Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostRepository repository;
	
	@Autowired
	private UserProxy proxy;
	
	@PostMapping("/post/post")
	public ResponseEntity<Object> postPost(@RequestBody Post post) {
		post.setPostType("post");
		post.setNumOfComments(0);
		post.setNumOfLikes(0);
		post.setPublished(new Date());
		post.setSeeable(!proxy.getPrivacy(post.getUserID()).getBody());
		Post savedPost = repository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedPost.getPostID()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/post/self/{id}")
	public ResponseEntity<List<Post>> profilePosts(@PathVariable long id) {
		List<Post> posts = repository.findByuserID(id);
		
		if(posts.isEmpty()) {
			return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/post/user/{id}/{myid}")
	public ResponseEntity<List<Post>> usersPosts(@PathVariable long id, @PathVariable long myid) {
		List<Post> posts= new ArrayList<>();
		if(proxy.getPrivacy(id).getBody()) {
			List<Long> ids = proxy.usersFollowingIds(id).getBody();
			if(ids.contains(myid)) {
				posts = repository.findByuserID(id);
				return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
			}
		}else {
		    posts = repository.findByuserID(id);
			return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		}
		if(posts.isEmpty()) {
			return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/post/liked/{id}")
	public ResponseEntity<List<Post>> getLiked(@PathVariable long id) {
		List<Post> posts= new ArrayList<>();
		posts =repository.findLikedPosts(id);
		if(posts.isEmpty()) {
			return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		
	}
	
	@GetMapping("/post/disliked/{id}")
	public ResponseEntity<List<Post>> getDisLiked(@PathVariable long id) {
		List<Post> posts= new ArrayList<>();
		posts =repository.findDislikedPosts(id);
		if(posts.isEmpty()) {
			return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		
	}
	
}
