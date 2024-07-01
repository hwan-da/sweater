package com.ssafy.sweater.model.dto;

public class Review {
	private int boardId;
	private int reviewId;
	private String title;
	private String writer;
	private String content;
	
	public Review() {}

	public Review(int boardId, int reviewId, String title, String writer, String content) {
		this.boardId = boardId;
		this.reviewId = reviewId;
		this.title = title;
		this.writer = writer;
		this.content = content;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Review [boardId=" + boardId + ", reviewId=" + reviewId + ", title=" + title + ", writer=" + writer
				+ ", content=" + content + "]";
	}
	
}
