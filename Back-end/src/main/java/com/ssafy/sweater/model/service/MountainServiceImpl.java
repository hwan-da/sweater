package com.ssafy.sweater.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sweater.model.dao.MountainDao;
import com.ssafy.sweater.model.dto.Mountain;

@Service
public class MountainServiceImpl implements MountainService {
	
	private MountainDao mountainDao;
	
	public MountainServiceImpl(MountainDao mountainDao){
		this.mountainDao = mountainDao;
	}
	
	@Override
	public List<Mountain> selectAll(String mntnCode) {
		return mountainDao.selectAll(mntnCode);
	}

}
