package com.ssafy.sweater.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sweater.model.dto.User;

public interface UserService {

	public User getUserByNickname(String nickname);
	
	public User getUserById(String userId);
	
	public int modifyUser(User user);
	
	public int removeUser(String userId);
	
	public int changePicture(MultipartFile picture, String userId);
	
}
