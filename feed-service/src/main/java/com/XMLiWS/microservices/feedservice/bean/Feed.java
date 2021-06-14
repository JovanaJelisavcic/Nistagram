package com.XMLiWS.microservices.feedservice.bean;

import java.util.ArrayList;

public class Feed {
	private Long id;
	private boolean feedType; //1-registered 0-unregistered
	private ArrayList<Post> posts;
	
	public Feed() {
		
	}
	
	
	public Feed(Long id, boolean feedType, ArrayList<Post> posts) {
		super();
		this.id = id;
		this.feedType = feedType;
		this.posts = posts;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isFeedType() {
		return feedType;
	}
	public void setFeedType(boolean feedType) {
		this.feedType = feedType;
	}
	public ArrayList<Post> getPosts() {
		return posts;
	}
	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}
	
	
}
