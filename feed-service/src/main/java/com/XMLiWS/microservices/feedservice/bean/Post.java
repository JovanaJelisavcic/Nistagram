package com.XMLiWS.microservices.feedservice.bean;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true,nullable=false,
    updatable=false)
	private Long postID;
	private String userID;
	@Column(nullable = true)
	private int numOfLikes;
	@Column(nullable = true)
	private int numOfComments;
	private LocalDateTime published;
	private String location;
	private boolean seeable;
	private String postType; 
	@ElementCollection
	@CollectionTable(
				name="url",
				joinColumns=@JoinColumn(name="postid"))
	@Column(name="url")
	private List<String> url;
	@Column(nullable = true)
	private String description;
	@JsonInclude()
	@Transient
	Set<String> hashtags = new HashSet<>();
	@JsonInclude()
	@Transient
	Set<String> profiletags = new HashSet<>();
	@ElementCollection
	@CollectionTable(
				name="liked",
				joinColumns=@JoinColumn(name="postid"))
	@Column(name="liked")
	private List<String> liked;

	@ElementCollection
	@CollectionTable(
				name="disliked",
				joinColumns=@JoinColumn(name="postid"))
	@Column(name="disliked")
	private List<String> disliked;

	
	public Post() {
	}
	
	


	public Post(String userID, int numOfLikes, int numOfComments, LocalDateTime published, List<String> url, boolean seeable, String location, String description) {
		super();
		this.setUserID(userID);
		this.numOfLikes = numOfLikes;
		this.numOfComments = numOfComments;
		this.published = published;
		this.url = url;
		this.seeable= seeable;
		this.location = location;
		this.description=description;

	}

	public Long getPostID() {
		return postID;
	}
	public void setPostID(Long postID) {
		this.postID = postID;
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
	public LocalDateTime getPublished() {
		return published;
	}
	public void setPublished(LocalDateTime published) {
		this.published = published;
	}
	public List<String> getUrl() {
		return url;
	}
	public void setUrl(List<String> url) {
		this.url = url;
	}


	public boolean isSeeable() {
		return seeable;
	}


	public void setSeeable(boolean seeable) {
		this.seeable = seeable;
	}


	public String getPostType() {
		return postType;
	}


	public void setPostType(String type) {
		this.postType = type;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	public Set<String> getHashtags() {
		return hashtags;
	}


	public void setHashtags(Set<String> hashtags) {
		this.hashtags = hashtags;
	}


	public Set<String> getProfiletags() {
		return profiletags;
	}


	public void setProfiletags(Set<String> profiletags) {
		this.profiletags = profiletags;
	}

	public List<String> getLiked() {
		return liked;
	}




	public void setLiked(List<String> liked) {
		this.liked = liked;
	}




	public List<String> getDisliked() {
		return disliked;
	}




	public void setDisliked(List<String> disliked) {
		this.disliked = disliked;
	}



	
	@PostLoad
	private void populateSets() {
		String des = StringUtils.appendIfMissing(description, " ");
		 String[] hashs = StringUtils.substringsBetween(des, "#", " ");
		 if(hashs!=null) {
	        for (String s : hashs) {
	            hashtags.add(s);
	        }
		 }
	        
	        String[] profs = StringUtils.substringsBetween(des, "@", " ");
	        if(profs!=null) {
	        for (String s : profs) {
	            profiletags.add(s);
	        }
	        }
		
	}




	public String getUserID() {
		return userID;
	}




	public void setUserID(String userID) {
		this.userID = userID;
	}
	

}
