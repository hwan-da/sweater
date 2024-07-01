package com.ssafy.sweater.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sweater.model.dao.ThunderDao;
import com.ssafy.sweater.model.dto.Thunder;

@Service
public class ThunderServiceImpl implements ThunderService {
	
	private final ThunderDao thunderDao;
	private final ResourceLoader loader;

	public ThunderServiceImpl(ThunderDao thunderDao, ResourceLoader loader) {
		this.thunderDao = thunderDao;
		this.loader = loader;
	}

	@Override
	public List<Thunder> getThunderByNickname(String nickname) {
		return thunderDao.getThunderByNickname(nickname);
	}

	@Override
	public Thunder readThunder(int thunderId) {
		return thunderDao.selectOne(thunderId);
	}

	@Override
	public List<Thunder> getThunderByTag(String tag) {
		return thunderDao.getThunderByTag(tag);
	}

	@Override
	public List<Thunder> getThunderByRegion(String region) {
		return thunderDao.getThunderByRegion(region);
	}
	
	@Override
	public List<Thunder> getThunderByHeart(String userId) {
		List<Integer> list = thunderDao.getThunderIdByHeart(userId);
		
		List<Thunder> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			result.add(thunderDao.getThunderByHeart(list.get(i)));
		}
		
		return result;
	}
	
	@Override
	public List<Thunder> selectAll() {
		return thunderDao.selectAll();
	}

	@Override
	public int writeThunder(Thunder thunder) {
		return thunderDao.insertThunder(thunder);
	}
	
	@Override
	public String fileUpload(MultipartFile multipartFile) {
		String URL = "http://localhost:8080/media/";

		String fileName = multipartFile.getOriginalFilename();

		Resource resource = loader.getResource("classpath:/static/media");
		// 전송을 하겠다.
		
		try {

			fileName = "thunder" + UUID.randomUUID().toString() + fileName;
			multipartFile.transferTo(new File(resource.getFile(), fileName));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return URL + fileName;

	}

	@Override
	public int modifyThunder(Thunder thunder) {
		return thunderDao.updateThunder(thunder);
	}

	@Override
	public int removeThunder(int thunderId) {
		return thunderDao.deleteThunder(thunderId);
	}

	@Override
	public int pushHeart(int thunderId, String userId, int isHeart) {
		Map map = new HashMap<>();
		map.put("thunderId", thunderId);
		map.put("userId", userId);
		
		if(isHeart == 0) {
			thunderDao.heartUp(thunderId);
			return thunderDao.insertHeart(map);
		}
		else {
			thunderDao.heartDown(thunderId);
			return thunderDao.deleteHeart(map);
		}
	}

	@Override
	public int isHeart(int thunderId, String userId) {
		Map map = new HashMap<>();
		map.put("thunderId", thunderId);
		map.put("userId", userId);
		
		return thunderDao.isHeart(map);
	}
	
	@Override
	public boolean checkHeart(int thunderId, String userId) {
		Map map = new HashMap<>();
		map.put("thunderId", thunderId);
		map.put("userId", userId);
		
		System.out.println(thunderDao.checkHeart(map));
		
		if(thunderDao.checkHeart(map) == 0) return false;
		
		return true;
	}

}
