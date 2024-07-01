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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sweater.model.dto.Review;
import com.ssafy.sweater.model.service.ReviewService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "ReviewRestController", description = "리뷰 관리")
public class ReviewRestController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private final ReviewService reviewService;
	
	@Autowired
	public ReviewRestController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	// 리뷰 전체 조회
	@GetMapping("/{boardId}")
	public ResponseEntity<?> list(@PathVariable("boardId") int boardId) {
		List<Review> result = reviewService.getReviewList(boardId);
		
		if(result == null || result.size() == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Review>>(result, HttpStatus.OK);
	}
	
	// 리뷰 작성
	@PostMapping("/{boardId}")
	public ResponseEntity<?> write(@PathVariable("boardId") int boardId, @RequestBody Review review) {
		review.setBoardId(boardId);
		int result = reviewService.writeReview(review);
		
		if(result == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
	}
	
	// 리뷰 수정
	@PutMapping("/{boardId}/{reviewId}")
	public ResponseEntity<?> modify(@PathVariable("boardId") int boardId, @PathVariable("reviewId") int reviewId, @RequestBody Review review){
		int result = reviewService.modifyReview(review);
		
		if(result == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	// 리뷰 삭제
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<?> delete(@PathVariable("reviewId") int reviewId){
		int result = reviewService.removeReview(reviewId);
		
		if(result == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
}	
