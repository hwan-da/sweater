package com.ssafy.sweater.model.dto;

import java.util.Date;

public class Board {
	private int boardId;
	private String region;
	private String tag;
	private String writer;
	private String title;
	private String header;
	private String content;
	private Date startDate;
	private Date endDate;
	private String media;
	private int heart;
	private String trail;
	private int hikingTime;
	private int hikingDist;
	
	public Board() {}

	public Board(int boardId, String region, String tag, String writer, String title, String header, Date startDate, Date endDate,
			String content, String media, int heart, String trail, int hikingTime, int hikingDist) {
		this.boardId = boardId;
		this.region = region;
		this.tag = tag;
		this.writer = writer;
		this.title = title;
		this.header = header;
		this.startDate = startDate;
		this.endDate = endDate;
		this.content = content;
		this.media = media;
		this.heart = heart;
		this.trail = trail;
		this.hikingTime = hikingTime;
		this.hikingDist = hikingDist;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public int getHeart() {
		return heart;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}

	public String getTrail() {
		return trail;
	}

	public void setTrail(String trail) {
		this.trail = trail;
	}

	public int getHikingTime() {
		return hikingTime;
	}

	public void setHikingTime(int hikingTime) {
		this.hikingTime = hikingTime;
	}

	public int getHikingDist() {
		return hikingDist;
	}

	public void setHikingDist(int hikingDist) {
		this.hikingDist = hikingDist;
	}

	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", region=" + region + ", tag=" + tag + ", writer=" + writer + ", title="
				+ title + ", header=" + header + ", content=" + content + ", startDate=" + startDate + ", endDate="
				+ endDate + ", media=" + media + ", heart=" + heart + ", trail=" + trail + ", hikingTime=" + hikingTime
				+ ", hikingDist=" + hikingDist + "]";
	}


	
}
