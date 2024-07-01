	package com.ssafy.sweater.model.dto.auth;

public class CheckCertification {
	
	private String userId;
	private String email;
	private String certificationNumber;
	
	public CheckCertification() {}

	public CheckCertification(String userId, String email, String certificationNumber) {
		this.userId = userId;
		this.email = email;
		this.certificationNumber = certificationNumber;
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

	public String getCertificationNumber() {
		return certificationNumber;
	}

	public void setCertificationNumber(String certificationNumber) {
		this.certificationNumber = certificationNumber;
	}

	@Override
	public String toString() {
		return "CheckCertification [userId=" + userId + ", email=" + email + ", certificationNumber="
				+ certificationNumber + "]";
	}
	
}
