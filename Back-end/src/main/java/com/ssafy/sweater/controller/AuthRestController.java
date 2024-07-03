package com.ssafy.sweater.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sweater.Util.JwtUtil;
import com.ssafy.sweater.model.dto.User;
import com.ssafy.sweater.model.dto.auth.CheckCertification;
import com.ssafy.sweater.model.dto.auth.EmailCertification;
import com.ssafy.sweater.model.dto.auth.SignIn;
import com.ssafy.sweater.model.dto.auth.SignUp;
import com.ssafy.sweater.model.service.AuthService;
import com.ssafy.sweater.model.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "AuthRestController", description = "인증 관리")
public class AuthRestController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private final AuthService authService;
	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	private final String CLIENT_ID = "privatekey";
	private final String REDIRECT_URI = "http://localhost:5173/kakao";
	
	
	public AuthRestController(AuthService authService, UserService userService, JwtUtil jwtUtil) {
		this.authService = authService;	
		this.userService = userService;	
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping("/id-check/{userId}")
	public ResponseEntity<?> idCheck(@PathVariable("userId") String userId){
		boolean result = authService.idCheck(userId);
		
		if(!result) return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@PostMapping("/email-certification")
	public ResponseEntity<?> emailCertification(@RequestBody EmailCertification emailCertification) {
//		System.out.println("이메일인증");
		
		boolean result = authService.emailCertification(emailCertification); 
		
		if(!result) return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@PostMapping("/check-certification")
	public ResponseEntity<?> checkCertification(@RequestBody CheckCertification checkCertification){
		boolean result = authService.checkCertification(checkCertification);
		
		if(!result) return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@RequestBody SignUp signUp){
//		System.out.println(signUp);
		int result = authService.signUp(signUp);
		
		if(result == 0) return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		
//		System.out.println(signUp.getUserId());
		authService.deleteCertification(signUp.getUserId());
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@PostMapping("/sign-in")
	public ResponseEntity<?> signIn(@RequestBody SignIn signIn){
		Map<String, Object> result = new HashMap<>();
		
		if(signIn.getUserId() == null) return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
		
		User user = userService.getUserById(signIn.getUserId());
		if(user == null) return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
		
		boolean check = user.getUserId().equals(signIn.getUserId()) && user.getPassword().equals(signIn.getPassword());
		if(!check) return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		
		result.put("message", SUCCESS);
		result.put("access_token", jwtUtil.createToken(signIn.getUserId()));
		result.put("userId", signIn.getUserId());
		
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> kakao() {
		String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI;
		
		return new ResponseEntity<String>(url, HttpStatus.OK);
	}
	
	@GetMapping("/kakao/{code}")
	public ResponseEntity<?> kakaoCallBack(@PathVariable String code) {
//		System.out.println(code);
		
		String kakaoToken = authService.getKakaoToken(code);
//		System.out.println("카카오토큰 : " + kakaoToken);
		
		HashMap<String, Object> userInfo = authService.getKakaoUser(kakaoToken);
//		System.out.println("userInfo : " + userInfo.toString());
		
		User user = userService.getUserById((String)userInfo.get("id"));
//		System.out.println(userInfo.get("id"));
//		System.out.println(user);
		
		if(user == null) {
			userInfo.put("isExisted", false);
		
		}
		
		else {
			userInfo.put("isExisted", true);
			userInfo.put("loginUser", user);
			userInfo.put("access_token", jwtUtil.createToken(userInfo.get("id").toString()));
		}
		
		
		return new ResponseEntity<HashMap<String, Object>>(userInfo, HttpStatus.OK);
	}
	
	@PostMapping("/kakao")
	public ResponseEntity<?> kakaoSignUp(@RequestBody SignUp signUp) {
//		System.out.println(signUp);
		
		int result = authService.kakaoSignUp(signUp);
		
		if(result == 0) return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	
}
