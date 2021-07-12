package com.XMLiWS.microservices.feedservice.bean;

import java.util.List;

public class Feed {

	private List<Post> posts;
	private List<Post> stories;
	
	public Feed() {}
	
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts2) {
		this.posts = posts2;
	}
	public List<Post> getStories() {
		return stories;
	}
	public void setStories(List<Post> stories2) {
		this.stories = stories2;
	}
}
