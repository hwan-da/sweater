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

import com.ssafy.sweater.model.dto.Comment;
import com.ssafy.sweater.model.dto.Message;
import com.ssafy.sweater.model.service.CommentService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "CommentRestController", description = "코멘트 관리")
public class CommentRestController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private final CommentService commentService;
	
	@Autowired
	public CommentRestController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	// 코멘트 전체 조회
	@GetMapping("/{thunderId}")
	public ResponseEntity<?> list(@PathVariable("thunderId") int thunderId) {
		List<Comment> result = commentService.getCommentList(thunderId);
		
		if(result == null || result.size() == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Comment>>(result, HttpStatus.OK);
	}
	
	// 코멘트 작성
	@PostMapping("/{thunderId}")
	public ResponseEntity<?> write(@PathVariable("thunderId") int thunderId, @RequestBody Comment comment) {
		//System.out.println(comment);
		
		comment.setThunderId(thunderId);
		int result = commentService.writeComment(comment);
		
		if(result == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
	}
	
	// 코멘트 수정
	@PutMapping("/{thunderId}/{commentId}")
	public ResponseEntity<?> modify(@PathVariable("thunderId") int thunderId, @PathVariable("commentId") int reviewId, @RequestBody Comment comment){
		int result = commentService.modifyComment(comment);
		
		if(result == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	// 코멘트 삭제
	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> delete(@PathVariable("commentId") int commentId){
		int result = commentService.removeComment(commentId);
		
		if(result == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	// 초대 메세지 전송
	@PostMapping("/invite")
	public ResponseEntity<?> invite(@RequestBody Message message){
		//System.out.println("초대 준비");
		message.setReceiver(message.getReceiver());
		int result = commentService.invite(message);
		
		if(result == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
}
