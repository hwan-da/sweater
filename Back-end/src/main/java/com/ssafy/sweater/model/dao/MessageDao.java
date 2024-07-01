package com.ssafy.sweater.model.dao;

import java.util.List;

import com.ssafy.sweater.model.dto.Message;

public interface MessageDao {
	
	public List<Message> selectAll(String userId);
	
	public Message selectOne(int messageId);

	public int remove(int messageId);
	
}
