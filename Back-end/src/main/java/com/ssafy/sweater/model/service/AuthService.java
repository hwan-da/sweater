package com.ssafy.sweater.model.service;

import java.util.HashMap;

import com.ssafy.sweater.model.dto.auth.CheckCertification;
import com.ssafy.sweater.model.dto.auth.EmailCertification;
import com.ssafy.sweater.model.dto.auth.SignUp;

public interface AuthService {
	
	public boolean idCheck(String userId);
	
	public boolean emailCertification(EmailCertification emailCertification);
	
	public boolean checkCertification(CheckCertification checkCertification);
	
	public int signUp(SignUp signUp);

	public int deleteCertification(String userId);
	
	public String getKakaoToken(String code);

	public HashMap<String, Object> getKakaoUser(String kakaoToken);
	
	public int kakaoSignUp(SignUp signUp);
	
	
	
}
