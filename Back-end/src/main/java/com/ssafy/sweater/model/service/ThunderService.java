package com.ssafy.sweater.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sweater.model.dto.Thunder;

public interface ThunderService {
	
	public List<Thunder> getThunderByNickname(String nickname);
	
	public Thunder readThunder(int thunderId);
	
	public List<Thunder> getThunderByTag(String tag);
	
	public List<Thunder> getThunderByRegion(String region);
	
	public List<Thunder> getThunderByHeart(String userId);

	public List<Thunder> selectAll();
	
	public int pushHeart(int thunderId, String userId, int isHeart);
	
	public int isHeart(int thunderId, String userId);
	
	public boolean checkHeart(int thunderId, String userId);
	
	public int writeThunder(Thunder thunder);
	
	public int modifyThunder(Thunder thunder);
	
	public int removeThunder(int thunderId);
	
	public String fileUpload(MultipartFile multipartFile);
	
	
}
