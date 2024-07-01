package com.ssafy.sweater.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.sweater.model.dto.Board;
import com.ssafy.sweater.model.dto.Thunder;

public interface BoardDao {
	
	public List<Board> getBoardByNickname(String nickname);
	
	public Board selectOne(int boardId);
	
	public List<Board> getBoardByTag(String tag);
	
	public List<Board> getBoardByRegion(String region);
	
	public Board getBoardByHeart(int boardId);
	
	public List<Integer> getBoardIdByHeart(String userId);
	
	public List<Board> selectAll();
	
	public int isHeart(Map<Integer, String> map);
	
	// contoller와, service에서는 push heart고 dao에서는 분리해서 메서드 사용
	public int heartUp(int boardId);
	
	public int heartDown(int boardId);
	
	public int insertHeart(Map<Integer, String> map);
	
	public int deleteHeart(Map<Integer, String> map);
	
	public int checkHeart(Map<Integer, String> map);
	
	public int insertBoard(Board board);

	public int updateBoard(Board board);
	
	public int deleteBoard(int boardId);

}
