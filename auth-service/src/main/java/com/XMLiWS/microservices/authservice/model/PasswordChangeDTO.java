package com.XMLiWS.microservices.authservice.model;

public class PasswordChangeDTO {
	private String oldPassword;
	private String password;
	public PasswordChangeDTO() {}
	
	public String getOldPassword() {
		return oldPassword;
	}
	
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPassword() {
		return password;
	}

	
}
