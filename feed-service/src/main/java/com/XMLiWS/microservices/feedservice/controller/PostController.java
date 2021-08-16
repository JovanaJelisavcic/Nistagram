package com.XMLiWS.microservices.feedservice.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.XMLiWS.microservices.feedservice.bean.Feed;
import com.XMLiWS.microservices.feedservice.bean.Post;
import com.XMLiWS.microservices.feedservice.proxy.UserProxy;
import com.XMLiWS.microservices.feedservice.repository.PostRepository;
import com.XMLiWS.microservices.feedservice.util.TokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@RequestMapping("/posts")
public class PostController {
	
	Logger logger = LoggerFactory.getLogger(PostController.class);
	
	String uploadFile = "http://localhost:8100/uploadFile";
	String displayFile = "http://localhost:8100/displayFile";

	@Autowired
	private PostRepository repository;
	
	@Autowired
	private UserProxy proxy;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@PostMapping(value = "/post", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Object> postPost(@RequestHeader("Authorization") String token, @RequestPart("files") MultipartFile[] files, @RequestParam("data") String data)
			throws JsonMappingException, JsonProcessingException {
		
		Post post = exctractPost(data);
		List<Integer> resp;
		if(tokenUtil.checkIdentity(post.getUserID(), token)) {
				if(post.getUrl().size()==1) {
				post.setPostType("post");
				}else {
					post.setPostType("album");
				}
				resp=uploadFiles(token,files);
				boolean  check = checkResponse(resp);
				if(!resp.isEmpty() && resp!=null && check){
				post.setNumOfComments(0);
				post.setNumOfLikes(0);
				post.setPublished( LocalDateTime.now());
				post.setSeeable(!proxy.getPrivacy(post.getUserID()).getBody());
				repository.save(post);
				return ResponseEntity.created(null).build();}
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}


	@PostMapping(value = "/story", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Object> postStory(@RequestHeader("Authorization")String token, @RequestPart("files") MultipartFile[] files,@RequestParam("data") String data) throws JsonMappingException, JsonProcessingException {
		Post post = exctractPost(data);
		
		if(tokenUtil.checkIdentity(post.getUserID(), token)) {
			List<Integer> resp=uploadFiles(token,files);
			boolean  check = checkResponse(resp);
			if(check) {
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
			else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}
		else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	
	@GetMapping("/{postId}")
	public ResponseEntity<Post> getPost(@RequestHeader("Authorization")String token, @PathVariable("postId") Long postId) throws URISyntaxException, IOException {
		if(checkAllowed(token, postId)) {
			List<Post> post = repository.findBypostID(postId);
			if(post.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<Post>(post.get(0), HttpStatus.OK);
			
		}else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
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

	
	
	
	@GetMapping("/liked/{username}")
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
	
	
	@GetMapping("/disliked/{username}")
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
	
	

	
	
private boolean checkResponse(List<Integer> resp) {
	for(Integer i : resp) {
		logger.info(i.toString());
		if(i.intValue()!=200) 
			return false;
	}
	return true;
}


private int uploadFile(String token, MultipartFile file) {
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	headers.add("Authorization", token);

	MultiValueMap<String, Object> body
	  = new LinkedMultiValueMap<>();
	body.add("file", file.getResource());
	
	HttpEntity<MultiValueMap<String, Object>> requestEntity
	 = new HttpEntity<>(body, headers);

	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> response = restTemplate
	  .postForEntity(uploadFile, requestEntity, String.class);
	return response.getStatusCodeValue();
}

/*
private Resource displayFile(String filename, String token) throws URISyntaxException {
	HttpHeaders headers = new HttpHeaders();
	headers.add("Authorization", token);
	RestTemplate restTemplate = new RestTemplate();
	URI uri =new URI(displayFile+"/"+filename);
	ResponseEntity<Resource> result = restTemplate.getForEntity(uri, Resource.class);
	return result.getBody();
}*/

private List<Integer> uploadFiles(String token, MultipartFile[] files) {
	List<Integer> resps = new ArrayList<>();
	for (MultipartFile file : files ) {
		resps.add(uploadFile(token,file));
	}
	return resps;
}

private Post exctractPost(String data) throws JsonMappingException, JsonProcessingException {
	logger.info(data);
	 Post post = new ObjectMapper().readValue(data, Post.class);  
	return post;
}

private boolean checkAllowed(String token, Long postId) {
	List<Post> post = repository.findBypostID(postId);
	if(post.get(0).getUserID().equals(tokenUtil.extractIdentity(token)))
		return true;
	else if(post.get(0).isSeeable())
		return true;
	else if(proxy.usersFollowers(post.get(0).getUserID()).getBody().contains(tokenUtil.extractIdentity(token)))
		return true;
	else return false;
	
	
}
	
}
