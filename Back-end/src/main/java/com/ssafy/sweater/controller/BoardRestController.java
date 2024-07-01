package com.ssafy.sweater.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.sweater.model.dto.Board;
import com.ssafy.sweater.model.dto.Thunder;
import com.ssafy.sweater.model.service.BoardService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/board")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "BoardRestController", description = "게시판 관리")
public class BoardRestController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	private final BoardService boardService;
	private final ObjectMapper objectMapper;

	@Autowired
	public BoardRestController(BoardService boardService, ObjectMapper objectMapper) {
		this.boardService = boardService;
		this.objectMapper = objectMapper;
	}

	// 게시글 전체 조회
	@GetMapping("/")
	public ResponseEntity<?> list() {
		List<Board> result = boardService.selectAll();

		if (result == null || result.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Board>>(result, HttpStatus.OK);
	}

	// 게시글 id로 조회(상세 조회)
	@GetMapping("/{boardId}")
	public ResponseEntity<?> detail(@PathVariable("boardId") int boardId) {
		Board result = boardService.readBoard(boardId);

		if (result == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Board>(result, HttpStatus.OK);
	}

	// 닉네임으로 조회
	@GetMapping("/nickname/{nickname}")
	public ResponseEntity<?> getListByNickname(@PathVariable("nickname") String nickname) {
		List<Board> result = boardService.getBoardByNickname(nickname);

		if (result == null || result.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Board>>(result, HttpStatus.OK);
	}

	// 태그로 조회
	@GetMapping("/tag/{tag}")
	public ResponseEntity<?> getListByTag(@PathVariable("tag") String tag) {
		List<Board> result = boardService.getBoardByTag(tag);

		if (result == null || result.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Board>>(result, HttpStatus.OK);
	}

	// 지역으로 조회
	@GetMapping("/region/{region}")
	public ResponseEntity<?> getListByRegion(@PathVariable("region") String region) {
		List<Board> result = boardService.getBoardByRegion(region);

		if (result == null || result.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Board>>(result, HttpStatus.OK);
	}
	
	// 좋아요 한 게시글 가져오기
	@GetMapping("/heart/{userId}")
	public ResponseEntity<?> getListByHeart(@PathVariable("userId") String userId){
//		System.out.println("오긴 함?");
		
		List<Board> result = boardService.getBoardByHeart(userId);
		
		if (result == null || result.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		//System.out.println(result);
		
		return new ResponseEntity<List<Board>>(result, HttpStatus.OK);
	}

	// 게시글 생성
	@PostMapping("/create")
	public ResponseEntity<?> write(@RequestParam("media") MultipartFile multipartFile,
			@RequestParam("board") String json) {
		
		if (multipartFile == null || multipartFile.getSize() == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}

		try {
			Board board = objectMapper.readValue(json, Board.class);

			String fileName = boardService.fileUpload(multipartFile);
//			System.out.println(fileName);

			board.setMedia(fileName);
			int result = boardService.writeBoard(board);

			if (result == 0) {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	// 게시글 수정
	@PutMapping("/update")
	public ResponseEntity<?> modify(@RequestBody Board board) {
		int result = boardService.modifyBoard(board);

		if (result == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	// 게시글 삭제
	@DeleteMapping("/{boardId}")
	public ResponseEntity<?> delete(@PathVariable("boardId") int boardId) {
		int result = boardService.removeBoard(boardId);

		if (result == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	// 게시글 좋아요
	// userId로 boardHeart에 해당 boardId가 있는지 확인
	// select 결과가 0 -> 좋아요 활성화시키기
	// select 결과가 1 -> 좋아요 비활성화시키기
	@PostMapping("/heart/{userId}/{boardId}")
	public ResponseEntity<?> heart(@PathVariable("userId") String userId, @PathVariable("boardId") int boardId) {
		int isHeart = boardService.isHeart(boardId, userId);

		boardService.pushHeart(boardId, userId, isHeart);

		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	// 게시글 좋아요 확인
	@PostMapping("/checkheart/{userId}/{boardId}")
	public ResponseEntity<?> checkHeart(@PathVariable("userId") String userId, @PathVariable("boardId") int boardId) {
		System.out.println("오냐?");
		boolean isHeart = boardService.checkHeart(boardId, userId);
		
		System.out.println(isHeart);
		
		if(!isHeart) return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

}
