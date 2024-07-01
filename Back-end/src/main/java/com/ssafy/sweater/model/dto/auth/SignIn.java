package com.ssafy.sweater.model.dto.auth;

public class SignIn {
	private String userId;
	private String password;
	
	public SignIn() {}

	public SignIn(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "SignIn [userId=" + userId + ", password=" + password + "]";
	}
	
}
