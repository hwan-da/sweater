package com.ssafy.sweater.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.sweater.model.dao.ReviewDao;
import com.ssafy.sweater.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService {

	private ReviewDao reviewDao;
	
	@Autowired
	public ReviewServiceImpl(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}
	
	@Override
	public List<Review> getReviewList(int boardId) {
		return reviewDao.selectAll(boardId);
	}

	@Override
	public int writeReview(Review review) {
		return reviewDao.insertReview(review);
	}

	@Override
	public int modifyReview(Review review) {
		return reviewDao.updateReview(review);
	}

	@Override
	public int removeReview(int reviewId) {
		return reviewDao.deleteReview(reviewId);
	}

}
