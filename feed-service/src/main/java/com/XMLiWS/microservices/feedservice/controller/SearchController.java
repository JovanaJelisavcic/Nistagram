package com.XMLiWS.microservices.feedservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.XMLiWS.microservices.feedservice.bean.Post;
import com.XMLiWS.microservices.feedservice.proxy.UserProxy;
import com.XMLiWS.microservices.feedservice.repository.PostRepository;
import com.XMLiWS.microservices.feedservice.util.TokenUtil;


@RestController
@RequestMapping("/search")
public class SearchController {
	
	Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private PostRepository repository;
	@Autowired
	private TokenUtil tokenUtil;
	@Autowired
	private UserProxy userProxy;
	
	@GetMapping("/searchLocation/{loc}")
	public ResponseEntity<List<Post>> searchLocation(@RequestHeader("Authorization") String token,@PathVariable String loc) {
		String username = tokenUtil.extractIdentity(token);
		 StringBuilder sb = new StringBuilder(loc.concat("%"));
		 sb.insert(0,"%");
		List<Post> posts = repository.findAllByLocationLikeIgnoreCase(sb.toString());
		List<Post> filtered = filterOnPrivacy(posts, username);
		if(filtered.isEmpty()) {
			return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Post>>(filtered, HttpStatus.OK);
	}
	
	@GetMapping("/public/searchLocation/{loc}")
	public ResponseEntity<List<Post>> searchLocationPublic(@PathVariable String loc) {
		 StringBuilder sb = new StringBuilder(loc.concat("%"));
		 sb.insert(0,"%");
		List<Post> posts = repository.findAllByLocationLikeIgnoreCaseAndSeeable(sb.toString(),true);
		
		if(posts.isEmpty()) {
			return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/searchHash/{hash}")
	public ResponseEntity<List<Post>> searchHash(@RequestHeader("Authorization") String token,@PathVariable String hash) {
		String username = tokenUtil.extractIdentity(token);
		List<Post> posts = repository.findAll();
		List<Post> filtered = filterHash(posts, hash);
		List<Post> filteredPrivacy = filterOnPrivacy(filtered, username);
		if(filteredPrivacy.isEmpty()) {
			return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Post>>(filteredPrivacy, HttpStatus.OK);
	}
	
	

	@GetMapping("/public/searchHash/{hash}")
	public ResponseEntity<List<Post>> searchHashPublic(@PathVariable String hash) {
		List<Post> posts = repository.findAllBySeeable(true);
		List<Post> filtered = filterHash(posts, hash);
		if(filtered.isEmpty()) {
			return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Post>>(filtered, HttpStatus.OK);
	}
	
	@GetMapping("/searchProfileHash/{hash}")
	public ResponseEntity<List<Post>> searchProfileHash(@RequestHeader("Authorization") String token,@PathVariable String hash) {
		String username = tokenUtil.extractIdentity(token);
		List<Post> posts = repository.findAll();
		List<Post> filtered = filterProfHash(posts, hash);
		List<Post> filteredPrivacy = filterOnPrivacy(filtered, username);
		if(filteredPrivacy.isEmpty()) {
			return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Post>>(filteredPrivacy, HttpStatus.OK);
	}
	
	
	@GetMapping("/public/searchProfileHash/{hash}")
	public ResponseEntity<List<Post>> searchProfileHashPublic(@PathVariable String hash) {
		List<Post> posts = repository.findAllBySeeable(true);
		List<Post> filtered = filterProfHash(posts, hash);
		if(filtered.isEmpty()) {
			return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Post>>(filtered, HttpStatus.OK);
	}

	private List<Post> filterHash(List<Post> posts,String hash) {
		List<Post> result = new ArrayList<>();
		for(Post post : posts) {
			for (String tag : post.getHashtags()) {
				if(tag.toLowerCase().contains(hash.toLowerCase())) {
					result.add(post);
					break;
				}
			}
		}
		return result;
	}
	
	private List<Post> filterProfHash(List<Post> posts, String hash) {
		List<Post> result = new ArrayList<>();
		for(Post post : posts) {
			for (String tag : post.getProfiletags()) {
				if(tag.toLowerCase().contains(hash.toLowerCase())) {
					result.add(post);
					break;
				}
			}
		}
		return result;
	}

	private List<Post> filterOnPrivacy(List<Post> filtered, String username) {
		List<Post> result = new ArrayList<>();
		for(Post post : filtered) {
			if(post.isSeeable()) { result.add(post);}
			else {
				if(username.equals(post.getUserID())) {result.add(post);}
				else {
					List<String> followers = userProxy.usersFollowers(post.getUserID()).getBody();
					if(followers.contains(username)) { result.add(post);}
				}
			}
		}
		return result;
	}
	
}
