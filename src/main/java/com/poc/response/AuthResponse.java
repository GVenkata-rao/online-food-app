package com.poc.response;

import com.poc.enums.User_Role;

public class AuthResponse {

	private String jwt;
	private String message;
	private User_Role role;
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User_Role getRole() {
		return role;
	}
	public void setRole(User_Role role) {
		this.role = role;
	}
	
	
}
