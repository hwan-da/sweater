package com.ssafy.sweater.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sweater.model.dao.MessageDao;
import com.ssafy.sweater.model.dto.Message;

@Service
public class MessageServiceImpl implements MessageService {

	private MessageDao messageDao;
	
	public MessageServiceImpl(MessageDao messageDao) {
		this.messageDao = messageDao;
	}
	
	@Override
	public List<Message> getMessageList(String nickname) {
		return messageDao.selectAll(nickname);
	}

	@Override
	public Message selectOne(int messageId) {
		return messageDao.selectOne(messageId);
	}

	@Override
	public int delete(int messageId) {
		return messageDao.remove(messageId);
	}

}
