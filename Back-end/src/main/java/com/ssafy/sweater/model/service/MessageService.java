package com.ssafy.sweater.model.service;

import java.util.List;

import com.ssafy.sweater.model.dto.Message;

public interface MessageService {
	
	public List<Message> getMessageList(String userId);
	
	public Message selectOne(int messageId);
	
	public int delete(int messageId);
}
