package com.ssafy.sweater.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sweater.model.dto.User;
import com.ssafy.sweater.model.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "UserRestController", description = "유저 관리")
public class UserRestController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private final UserService userService;
	
	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/id/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable("userId") String userId){
		User user = userService.getUserById(userId);
		
		if(user == null) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("/nickname/{nickname}")
	public ResponseEntity<?> getUserByNickname(@PathVariable("nickname") String nickname){
		User user = userService.getUserByNickname(nickname);
		
		
		
		if(user == null) {
			return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> update(@RequestBody User user) {
		//System.out.println(user);
		
		String userId = user.getUserId();
		User beforeUser = userService.getUserById(userId);
		
		user.setPassword(beforeUser.getPassword());
		user.setNickname(beforeUser.getNickname());
		user.setGender(beforeUser.getGender());
		
		int result = userService.modifyUser(user);
		
		if(result == 0) {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
	@PostMapping("/profile")
	public ResponseEntity<?> profileUpdate(@RequestParam("picture") MultipartFile picture, @RequestParam("userId") String userId){
		
		if(picture == null || picture.getSize() == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}
		
		try {
			int result = userService.changePicture(picture, userId);
			
			if (result == 0) {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Void>(HttpStatus.OK);
			
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> delete(@PathVariable("userId") String userId){
		int result = userService.removeUser(userId);
		
		if(result == 0) return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}
