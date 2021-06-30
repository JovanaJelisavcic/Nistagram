package com.XMLiWS.microservices.userservice.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = { "following", "followers" })
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	private String username;
	private String name;
	private String surname;
	private String email;
	private String phoneNumber;
	private boolean sex;
	private Date birthday;
	private String website;
	private String bio;
	private boolean privacy;
	
	@OneToMany(mappedBy="to")
	private List<Followers> followers;

	@OneToMany(mappedBy="from")
	private List<Followers> following;

	public User() {
		
	}

	public User(String username, String name, String surname, String email, String phoneNumber,
			boolean sex, Date birthday, String website, String bio, boolean privacy) {
		super();
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.sex = sex;
		this.birthday = birthday;
		this.website = website;
		this.bio = bio;
		this.privacy = privacy;
	}
	

    public List<Followers> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Followers> followers) {
		this.followers = followers;
	}

	public List<Followers> getFollowing() {
		return following;
	}

	public void setFollowing(List<Followers> following) {
		this.following = following;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public boolean isPrivacy() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}

	
}
