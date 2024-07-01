package com.ssafy.sweater.model.dto.auth;

public class EmailCertification {
	
	private String userId;
	private String email;
	
	public EmailCertification() {}

	public EmailCertification(String userId, String email) {
		this.userId = userId;
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmailCertification [userId=" + userId + ", email=" + email + "]";
	}
	
}
