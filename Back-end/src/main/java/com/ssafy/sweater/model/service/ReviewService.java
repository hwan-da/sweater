package com.ssafy.sweater.model.service;

import java.util.List;

import com.ssafy.sweater.model.dto.Review;

public interface ReviewService {

	public List<Review> getReviewList(int boardId);

	public int writeReview(Review review);

	public int modifyReview(Review review);

	public int removeReview(int reviewId);
	
}
