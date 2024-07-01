package com.ssafy.sweater.model.dao;

import java.util.List;

import com.ssafy.sweater.model.dto.auth.CheckCertification;
import com.ssafy.sweater.model.dto.auth.EmailCertification;
import com.ssafy.sweater.model.dto.auth.SignUp;

public interface AuthDao {
	
	public int idCheck(String userId);
	
	public int emailCertification(EmailCertification emailCertification);
	
	public int checkCertification(CheckCertification checkCertification);
	
	public int signUp(SignUp signUp);
	
	public int add(CheckCertification checkCertification);
	
	public int delete(String userId);
	
	public List<CheckCertification> check(String userId);
	
}
