package com.ssafy.sweater.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sweater.model.dao.BoardDao;
import com.ssafy.sweater.model.dto.Board;
import com.ssafy.sweater.model.dto.Thunder;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;

	private final ResourceLoader loader;

	public BoardServiceImpl(BoardDao boardDao, ResourceLoader loader) {
		this.boardDao = boardDao;
		this.loader = loader;
	}

	@Override
	public List<Board> getBoardByNickname(String nickname) {
		return boardDao.getBoardByNickname(nickname);
	}

	@Override
	public Board readBoard(int boardId) {
		return boardDao.selectOne(boardId);
	}

	@Override
	public List<Board> getBoardByTag(String tag) {
		return boardDao.getBoardByTag(tag);
	}

	@Override
	public List<Board> getBoardByRegion(String region) {
		return boardDao.getBoardByRegion(region);
	}
	
	@Override
	public List<Board> getBoardByHeart(String userId) {
		List<Integer> list = boardDao.getBoardIdByHeart(userId);
		
		List<Board> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			result.add(boardDao.getBoardByHeart(list.get(i)));
		}
		
		return result;
	}
	
	@Override
	public List<Board> selectAll() {
		return boardDao.selectAll();
	}

	@Override
	public int writeBoard(Board board) {
		return boardDao.insertBoard(board);
	}

	@Override
	public String fileUpload(MultipartFile multipartFile) {
		String URL = "http://localhost:8080/media/";

		String fileName = multipartFile.getOriginalFilename();

		Resource resource = loader.getResource("classpath:/static/media");
		// 전송을 하겠다.
		
		try {

			fileName = "board" + UUID.randomUUID().toString() + fileName;
			multipartFile.transferTo(new File(resource.getFile(), fileName));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return URL + fileName;

	}

	@Override
	public int modifyBoard(Board board) {
		return boardDao.updateBoard(board);
	}

	@Override
	public int removeBoard(int boardId) {
		return boardDao.deleteBoard(boardId);
	}

	@Override
	public int pushHeart(int boardId, String userId, int isHeart) {
		Map map = new HashMap<>();
		map.put("boardId", boardId);
		map.put("userId", userId);

		if (isHeart == 0) {
			boardDao.heartUp(boardId);
			return boardDao.insertHeart(map);
		} else {
			boardDao.heartDown(boardId);
			return boardDao.deleteHeart(map);
		}
	}

	@Override
	public int isHeart(int boardId, String userId) {
		Map map = new HashMap<>();
		map.put("boardId", boardId);
		map.put("userId", userId);

		return boardDao.isHeart(map);
	}

	@Override
	public boolean checkHeart(int boardId, String userId) {
		Map map = new HashMap<>();
		map.put("boardId", boardId);
		map.put("userId", userId);
		
		System.out.println(boardDao.checkHeart(map));
		
		if(boardDao.checkHeart(map) == 0) return false;
		
		return true;
	}

}
