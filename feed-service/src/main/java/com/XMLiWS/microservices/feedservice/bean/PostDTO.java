package com.XMLiWS.microservices.feedservice.bean;

import java.util.List;

public class PostDTO {
/*{
            userID: "mrubinov0",
            url: ["flores.jpg", "let.jpg"],
            location : "Danicareva 24, Beograd",
            description : "art lovers #art #lovers"
        }*/
	
	
	private String userID;
	private List<String> url;
	private String location;
	private String description;
	public PostDTO() {}
	
	public PostDTO(String userID, List<String> url, String location, String description) {
		super();
		this.userID = userID;
		this.url = url;
		this.location = location;
		this.description = description;
	}

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public List<String> getUrl() {
		return url;
	}
	public void setUrl(List<String> url) {
		this.url = url;
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
	
	
}
