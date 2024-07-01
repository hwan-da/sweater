package com.ssafy.sweater.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sweater.model.dto.Board;
import com.ssafy.sweater.model.dto.Thunder;

public interface BoardService {

	public List<Board> getBoardByNickname(String nickname);
	
	public Board readBoard(int boardId);
	
	public List<Board> getBoardByTag(String tag);
	
	public List<Board> getBoardByRegion(String region);
	
	public List<Board> getBoardByHeart(String userId);
	
	public List<Board> selectAll();
	
	public int pushHeart(int boardId, String userId, int isHeart);
	
	public int isHeart(int boardId, String userId);
	
	public boolean checkHeart(int boardId, String userId);
	
	public int writeBoard(Board board);
	
	public int modifyBoard(Board board);
	
	public int removeBoard(int boardId);

	public String fileUpload(MultipartFile multipartFile);
	
	
	
}
