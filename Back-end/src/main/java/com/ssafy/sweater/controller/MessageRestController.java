package com.ssafy.sweater.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sweater.model.dto.Message;
import com.ssafy.sweater.model.service.MessageService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "MessageRestController", description = "메세지 관리")
public class MessageRestController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private final MessageService messageService;
	
	@Autowired
	public MessageRestController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	// 메세지 전체 조회
	@GetMapping("/{nickname}")
	public ResponseEntity<?> list(@PathVariable("nickname") String nickname){
		List<Message> result = messageService.getMessageList(nickname);
		
		if(result == null || result.size() == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Message>>(result, HttpStatus.OK);
	}
	
	// 메세지 상세 조회
//	@GetMapping("/detail/{messageId}")
//	public ResponseEntity<?> detail(@PathVariable("messageId") int messageId){
//		Message result = messageService.selectOne(messageId);
//		
//		if(result == null) {
//			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//		}
//		
//		return new ResponseEntity<Message>(result, HttpStatus.OK);
//	}
//	
	// 메세지 삭제
	@DeleteMapping("/{messageId}")
	public ResponseEntity<?> delete(@PathVariable("messageId") int messageId){
		int result = messageService.delete(messageId);
		
		if(result == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	
}
