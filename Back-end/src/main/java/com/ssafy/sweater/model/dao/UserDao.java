package com.ssafy.sweater.model.dao;

import com.ssafy.sweater.model.dto.User;

public interface UserDao {
	
	public boolean login();
	
	public int signup(User user);
	
	public User getUserByNickname(String nickname);
	
	public User getUserById(String userId);
	
	public int updateUser(User user);
	
	public int deleteUser(String userId);
	
	public boolean checkId(String userId);
	
	public boolean checkNn(String nickname);
	
	public int updatePicture(User user);
	
}
