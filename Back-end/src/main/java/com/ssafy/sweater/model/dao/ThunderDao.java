package com.ssafy.sweater.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.sweater.model.dto.Thunder;

public interface ThunderDao {

	public List<Thunder> getThunderByNickname(String nickname);

	public Thunder selectOne(int thunderId);

	public List<Thunder> getThunderByTag(String tag);

	public List<Thunder> getThunderByRegion(String region);

	public Thunder getThunderByHeart(int thunderId);
	
	public List<Integer> getThunderIdByHeart(String userId);

	public List<Thunder> selectAll();

	public int isHeart(Map<Integer, String> map);

	// contoller와, service에서는 push heart고 dao에서는 분리해서 메서드 사용
	public int heartUp(int boardId);

	public int heartDown(int boardId);

	public int insertHeart(Map<Integer, String> map);

	public int deleteHeart(Map<Integer, String> map);
	
	public int checkHeart(Map<Integer, String> map);

	public int insertThunder(Thunder thunder);

	public int updateThunder(Thunder thunder);

	public int deleteThunder(int thunderId);

}
