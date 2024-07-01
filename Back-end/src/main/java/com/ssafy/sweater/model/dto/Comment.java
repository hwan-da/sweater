package com.ssafy.sweater.model.dto;

public class Comment {
	private int thunderId;
	private int commentId;
	private String title;
	private String writer;
	private String content;
	
	public Comment() {}

	public Comment(int thunderId, int commentId, String title, String writer, String content) {
		super();
		this.thunderId = thunderId;
		this.commentId = commentId;
		this.title = title;
		this.writer = writer;
		this.content = content;
	}

	public int getThunderId() {
		return thunderId;
	}

	public void setThunderId(int thunderId) {
		this.thunderId = thunderId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
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
		return "Comment [thunderId=" + thunderId + ", commentId=" + commentId + ", title=" + title + ", writer="
				+ writer + ", content=" + content + "]";
	}
	
}
