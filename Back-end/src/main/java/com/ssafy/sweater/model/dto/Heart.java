package com.ssafy.sweater.model.dto;

public class Heart {
	private int boardId;
	private String userId;
	private boolean isHeart;
	
	public Heart() {}

	public Heart(int boardId, String userId, boolean isHeart) {
		super();
		this.boardId = boardId;
		this.userId = userId;
		this.isHeart = isHeart;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isHeart() {
		return isHeart;
	}

	public void setHeart(boolean isHeart) {
		this.isHeart = isHeart;
	}

	@Override
	public String toString() {
		return "Heart [boardId=" + boardId + ", userId=" + userId + ", isHeart=" + isHeart + "]";
	};
	
}
