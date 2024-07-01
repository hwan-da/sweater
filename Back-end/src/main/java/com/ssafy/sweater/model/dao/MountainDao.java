package com.ssafy.sweater.model.dao;

import java.util.List;

import com.ssafy.sweater.model.dto.Mountain;

public interface MountainDao {
	
	public List<Mountain> selectAll(String mntnCode);
	
}
