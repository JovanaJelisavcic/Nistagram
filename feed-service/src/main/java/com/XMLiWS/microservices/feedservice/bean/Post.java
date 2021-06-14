package com.XMLiWS.microservices.feedservice.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Post {
	
	@Id
	private Long postID;
	private Long userID;
	private int numOfLikes;
	private int numOfComments;
	private Date published;
	private String url;
	private boolean seeable;
	
	public Post() {
		
	}
	
	
	public Post(Long postID, Long userID, int numOfLikes, int numOfComments, Date published, String url, boolean seeable) {
		super();
		this.postID = postID;
		this.userID = userID;
		this.numOfLikes = numOfLikes;
		this.numOfComments = numOfComments;
		this.published = published;
		this.url = url;
		this.seeable= seeable;
	}
	
	public Long getPostID() {
		return postID;
	}
	public void setPostID(Long postID) {
		this.postID = postID;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public int getNumOfLikes() {
		return numOfLikes;
	}
	public void setNumOfLikes(int numOfLikes) {
		this.numOfLikes = numOfLikes;
	}
	public int getNumOfComments() {
		return numOfComments;
	}
	public void setNumOfComments(int numOfComments) {
		this.numOfComments = numOfComments;
	}
	public Date getPublished() {
		return published;
	}
	public void setPublished(Date published) {
		this.published = published;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}


	public boolean isSeeable() {
		return seeable;
	}


	public void setSeeable(boolean seeable) {
		this.seeable = seeable;
	}

}
