package com.ssafy.sweater.model.dto.auth;

public class SignUp {
	
	private String userId;
	private String password;
	private String nickname;
	private String email;
	private String gender;
	private String certificationNumber;
	
	public SignUp() {}

	public SignUp(String userId, String password, String nickname, String email, String gender,
			String certificationNumber) {
		this.userId = userId;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.gender = gender;
		this.certificationNumber = certificationNumber;
	}
	
	public SignUp(String userId, String password, String nickname, String gender) {
		this.userId = userId;
		this.password = password;
		this.nickname = nickname;
		this.email = "";
		this.gender = gender;
		this.certificationNumber = "";
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCertificationNumber() {
		return certificationNumber;
	}

	public void setCertificationNumber(String certificationNumber) {
		this.certificationNumber = certificationNumber;
	}

	@Override
	public String toString() {
		return "SignUp [userId=" + userId + ", password=" + password + ", nickname=" + nickname + ", email=" + email
				+ ", gender=" + gender + ", certificationNumber=" + certificationNumber + "]";
	}
	
}
