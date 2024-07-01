package com.ssafy.sweater.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sweater.model.dto.Mountain;
import com.ssafy.sweater.model.service.MountainService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/mountain")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "MountainRestController", description = "산 지점 관리")
public class MountainRestController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private final MountainService mountainService;
	
	@Autowired
	public MountainRestController(MountainService mountainService) {
		this.mountainService = mountainService;
	}
	
	// 산 경로 조회
	@GetMapping("/{mntn_code}")
	public ResponseEntity<?> list(@PathVariable("mntn_code") String mntnCode){
		List<Mountain> result = mountainService.selectAll(mntnCode);
		
		if(result == null || result.size() == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Mountain>>(result, HttpStatus.OK);
	}
	
}
