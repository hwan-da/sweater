package com.ssafy.sweater.model.service;

import java.util.List;

import com.ssafy.sweater.model.dto.Mountain;

public interface MountainService {

	public List<Mountain> selectAll(String mntnCode);
	
}
