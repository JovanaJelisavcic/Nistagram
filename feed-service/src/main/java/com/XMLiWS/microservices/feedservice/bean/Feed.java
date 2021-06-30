package com.XMLiWS.microservices.feedservice.bean;

import java.util.ArrayList;



public class Feed {

	private boolean feedType;
	private ArrayList<Post> posts;
	
	public Feed() {
		
	}
	
	
	public Feed(boolean feedType, ArrayList<Post> posts) {
		super();
		this.feedType = feedType;
		this.posts = posts;
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
