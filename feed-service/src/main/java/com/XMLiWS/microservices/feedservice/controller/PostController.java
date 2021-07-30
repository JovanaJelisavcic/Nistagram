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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.XMLiWS.microservices.feedservice.bean.Feed;
import com.XMLiWS.microservices.feedservice.bean.Post;
import com.XMLiWS.microservices.feedservice.proxy.UserProxy;
import com.XMLiWS.microservices.feedservice.repository.PostRepository;
import com.XMLiWS.microservices.feedservice.util.TokenUtil;



@RestController
public class PostController {
	
	Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostRepository repository;
	
	@Autowired
	private UserProxy proxy;
	@Autowired
	private TokenUtil tokenUtil;
	
	@PostMapping("/post/post")
	public ResponseEntity<Object> postPost(@RequestHeader("Authorization")String token, @RequestBody Post post) {
		if(tokenUtil.checkIdentity(post.getUserID(), token)) {
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
		return ResponseEntity.created(null).build();}
		else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@PostMapping("/post/story")
	public ResponseEntity<Object> postStory(@RequestHeader("Authorization")String token,@RequestBody Post post) {
		if(tokenUtil.checkIdentity(post.getUserID(), token)) {
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
		return ResponseEntity.created(null).build();}
		else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@GetMapping("/myProfile")
	public ResponseEntity<Feed> profilePosts(@RequestHeader("Authorization")String token) {
		String username = tokenUtil.extractIdentity(token);
		List<Post> postsAndStories = repository.findByuserID(username);
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
	
	@GetMapping("/userProfile/{username}")
	public ResponseEntity<Feed> usersPosts(@RequestHeader("Authorization")String token,@PathVariable String username) {
		String meUser = tokenUtil.extractIdentity(token);
		if(proxy.getPrivacy(username).getBody()) {
			List<String> followings = proxy.usersFollowers(username).getBody();
				if(followings.contains(meUser)) {
					Feed feed= getPosts(username);
					return new ResponseEntity<Feed>(feed, HttpStatus.OK);
				}else return new ResponseEntity<Feed>(HttpStatus.UNAUTHORIZED);
		}else {	
			Feed feed = getPosts(username);
			return new ResponseEntity<Feed>(feed, HttpStatus.OK);
		}
		

	}
	
	@GetMapping("/public/userProfile/{username}")
	public ResponseEntity<Feed> usersPostsPublic(@PathVariable String username) {
		if(!proxy.getPrivacy(username).getBody()) {
			Feed feed = getPosts(username);
			if(feed.getPosts().isEmpty() && feed.getStories().isEmpty()) return new ResponseEntity<Feed>(HttpStatus.NOT_FOUND);
			else return new ResponseEntity<Feed>(feed, HttpStatus.OK);
		}else {	
			return new ResponseEntity<Feed>(HttpStatus.UNAUTHORIZED);
		}
		

	}

	
	
	
	@GetMapping("/post/liked/{username}")
	public ResponseEntity<List<Post>> getLiked(@RequestHeader("Authorization")String token,@PathVariable String username) {
		String meUser = tokenUtil.extractIdentity(token);
		List<Post> posts = new ArrayList<>();
		if(username.equals(meUser)) {
			posts = getLiked(username);
		}else {
			if(!proxy.getPrivacy(username).getBody()) {
				posts = getLiked(username);
			}else {
				List<String> followings = proxy.usersFollowers(username).getBody();
				if(followings.contains(meUser)) {
					posts = getLiked(username);
				}else return new ResponseEntity<List<Post>>(HttpStatus.UNAUTHORIZED);
			}
			
		}
		
		if(posts.isEmpty()) return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		else return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/post/disliked/{username}")
	public ResponseEntity<List<Post>> getDisiked(@RequestHeader("Authorization")String token,@PathVariable String username) {
		String meUser = tokenUtil.extractIdentity(token);
		List<Post> posts = new ArrayList<>();
		if(username.equals(meUser)) {
			posts = getDisiked(username);
		}else {
			if(!proxy.getPrivacy(username).getBody()) {
				posts = getDisiked(username);
			}else {
				List<String> followings = proxy.usersFollowers(username).getBody();
				if(followings.contains(meUser)) {
					posts = getDisiked(username);
				}else return new ResponseEntity<List<Post>>(HttpStatus.UNAUTHORIZED);
			}
			
		}
		
		if(posts.isEmpty()) return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		else return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		
	}
	
	private List<Post> getLiked(String username) {
		return repository.findLikedPosts(username);
	}
	
	private List<Post> getDisiked(String username) {
		return repository.findDislikedPosts(username);
	}

	
	
	private Feed getPosts(String username) {
		List<Post> postsAndStories = repository.findByuserID(username);
		 Map<Boolean, List<Post>> groups = 
		 postsAndStories.stream().collect(Collectors.partitioningBy( s -> s.getPostType().equalsIgnoreCase("story")));

		 List<Post> posts = groups.get(false);
		 List<Post> stories = groups.get(true);
		 List<Post> nowStories = filterStories(stories); 
		Feed feed =  new Feed();
		feed.setPosts(posts);
		feed.setStories(nowStories);
		return feed;
	}

	private List<Post> filterStories(List<Post> stories) {
		List<Post> result = new ArrayList<>();
		for(Post post : stories) {
			if(LocalDateTime.now().minusHours(24).isBefore(post.getPublished())){
				result.add(post);
			}
		}
		return result;
	}
	
	
}
