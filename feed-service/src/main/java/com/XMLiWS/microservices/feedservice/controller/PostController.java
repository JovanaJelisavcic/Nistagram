package com.XMLiWS.microservices.feedservice.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		post.setSeeable(proxy.getPrivacy(post.getUserID()));
		Post savedPost = repository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedPost.getPostID()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/post/self/{id}")
	public List<Post> profilePosts(@PathVariable long id) {
		List<Post> posts = repository.findByuserID(id);
		
		if(posts.isEmpty()) {
			throw new RuntimeException("No posts yet");
		}
		return posts;
	}
	
	@GetMapping("/post/user/{id}/{myid}")
	public List<Post> usersPosts(@PathVariable long id, @PathVariable long myid) {
		List<Post> posts= null;
		if(proxy.getPrivacy(id)) {
			List<Long> ids = proxy.usersFollowingIds(id);
			if(ids.contains(myid)) {
				posts = repository.findByuserID(id);
				return posts;
			}
		}else {
		    posts = repository.findByuserID(id);
			return posts;
		}
		if(posts.isEmpty()) {
			throw new RuntimeException("No posts yet");
		}
		return posts;
		
	}

}
