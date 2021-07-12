package com.XMLiWS.microservices.feedservice.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.XMLiWS.microservices.feedservice.bean.Feed;
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
		if(post.getUrl().size()==1) {
		post.setPostType("post");
		}else {
			post.setPostType("album");
		}
		post.setNumOfComments(0);
		post.setNumOfLikes(0);
		post.setPublished( LocalDateTime.now());
		post.setSeeable(!proxy.getPrivacy(post.getUserID()).getBody());
		repository.save(post);
		return ResponseEntity.created(null).build();
	}
	
	@PostMapping("/post/story")
	public ResponseEntity<Object> postStory(@RequestBody Post post) {
		if(post.getUrl().size()==1) {
		post.setPostType("story");
		post.setDescription("");
		post.setLocation("");
		post.setNumOfComments(0);
		post.setNumOfLikes(0);
		post.setPublished( LocalDateTime.now());
		post.setSeeable(!proxy.getPrivacy(post.getUserID()).getBody());
		repository.save(post);
		} else {
			for(String url : post.getUrl()) {
				Post story = new Post();
				post.setDescription("");
				post.setLocation("");
				story.setUserID(post.getUserID());
				story.setPostType("story");
				story.setNumOfComments(0);
				story.setNumOfLikes(0);
				story.setPublished( LocalDateTime.now());
				story.setSeeable(!proxy.getPrivacy(post.getUserID()).getBody());
				List<String> thisUrl = new ArrayList<>();
				thisUrl.add(url);
				story.setUrl(thisUrl);
				repository.save(story);
			}
		}
		return ResponseEntity.created(null).build();
	}
	
	@GetMapping("/post/selfProfile/{id}")
	public ResponseEntity<Feed> profilePosts(@PathVariable long id) {
		List<Post> postsAndStories = repository.findByuserID(id);
		 Map<Boolean, List<Post>> groups = 
		 postsAndStories.stream().collect(Collectors.partitioningBy( s -> s.getPostType().equalsIgnoreCase("story")));

		 List<Post> posts = groups.get(false);
		 List<Post> stories = groups.get(true);
		if(posts.isEmpty() && stories.isEmpty()) {
			return new ResponseEntity<Feed>(HttpStatus.NOT_FOUND);
		}
		Feed feed =  new Feed();
		feed.setPosts(posts);
		feed.setStories(stories);
		return new ResponseEntity<Feed>(feed, HttpStatus.OK);
	}
	
	@GetMapping("/post/userProfile/{id}/{myid}")
	public ResponseEntity<Feed> usersPosts(@PathVariable long id, @PathVariable long myid) {
		if(proxy.getPrivacy(id).getBody()) {
			List<Long> ids = proxy.usersFollowingIds(id).getBody();
			if(ids.contains(myid)) {
				return profilePosts(id);
			}
		}else {	
			return profilePosts(id);
		}
		return null;

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
