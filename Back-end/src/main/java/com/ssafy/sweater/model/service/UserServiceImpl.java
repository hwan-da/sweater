package com.ssafy.sweater.model.service;

import java.io.File;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sweater.model.dao.UserDao;
import com.ssafy.sweater.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserDao userDao;
	private final ResourceLoader loader;
	
	public UserServiceImpl(UserDao userDao, ResourceLoader loader) {
		this.userDao = userDao;
		this.loader = loader;
	}
	
	@Override
	public User getUserByNickname(String nickname) {
		return userDao.getUserByNickname(nickname);
	}

	@Override
	public User getUserById(String userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public int modifyUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public int removeUser(String userId) {
		return userDao.deleteUser(userId);
	}

	@Override
	public int changePicture(MultipartFile picture, String userId) {
		int result = 0;
		
		String URL = "http://localhost:8080/media/";
		
		String fileName = picture.getOriginalFilename();
		
		Resource resource = loader.getResource("classpath:/static/media");
		
//		System.out.println("안녕");
		try {
			fileName = "picture" + UUID.randomUUID().toString() + fileName;
			picture.transferTo(new File(resource.getFile(), fileName));
			
			User user = new User();
			user.setUserId(userId);
			user.setPicture(URL + fileName);
			
			result = userDao.updatePicture(user);
			
//			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
