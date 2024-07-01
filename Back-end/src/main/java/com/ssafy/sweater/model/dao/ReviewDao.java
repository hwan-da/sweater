package com.ssafy.sweater.model.dao;

import java.util.List;

import com.ssafy.sweater.model.dto.Review;

public interface ReviewDao {
	
	public List<Review> selectAll(int boardId);
	
	public int insertReview(Review review);
	
	public int updateReview(Review review);
	
	public int deleteReview(int reviewId);
	
}
