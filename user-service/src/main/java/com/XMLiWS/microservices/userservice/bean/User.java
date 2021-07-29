package com.XMLiWS.microservices.userservice.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.XMLiWS.microservices.userservice.view.View;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@JsonIgnoreProperties(value = { "following", "followers" })
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true,nullable=false,
	        updatable=false)
	@JsonView(View.Simple.class)
	private Long userId;
	@Column(unique=true, nullable=false,
	        updatable=false)
	@JsonView(View.Simple.class)
	private String username;
	@JsonView(View.Simple.class)
	private String name;
	@JsonView(View.Simple.class)
	private String surname;
	@JsonView(View.Detailed.class)
	private String email;
	@JsonView(View.Detailed.class)
	private String phoneNumber;
	@JsonView(View.Detailed.class)
	private boolean sex;
	@JsonView(View.Detailed.class)
	private Date birthday;
	@JsonView(View.Detailed.class)
	private String website;
	@JsonView(View.Detailed.class)
	private String bio;
	@JsonView(View.Simple.class)
	private boolean privacy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	@JsonView(View.Detailed.class)
	private String password;
	@OneToMany(mappedBy="to")
	@JsonView(View.Simple.class)
	private List<Followers> followers;

	@OneToMany(mappedBy="from")
	@JsonView(View.Simple.class)
	private List<Followers> following;

	public User() {
		
	}

	public User(String username, String name, String surname, String email, String phoneNumber,
			boolean sex, Date birthday, String website, String bio, boolean privacy, String password) {
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
		this.password= password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
