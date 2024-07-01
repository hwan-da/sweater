package com.ssafy.sweater.model.dto;

public class User {
	private String userId;
	private String password;
	private String nickname;
	private String gender;
	private String picture;
	private String introduce;
	private String badge;
	
	
	public User() {};
	
	public User(String userId, String password, String nickname, String gender, String picture, String introduce,
			String badge) {
		this.userId = userId;
		this.password = password;
		this.nickname = nickname;
		this.gender = gender;
		this.picture = picture;
		this.introduce = introduce;
		this.badge = badge;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}



	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", nickname=" + nickname + ", gender=" + gender
				+ ", picture=" + picture + ", introduce=" + introduce + ", badge=" + badge + ", follower=" + "]";
	}
	
}
