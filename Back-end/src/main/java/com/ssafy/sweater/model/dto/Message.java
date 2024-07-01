package com.ssafy.sweater.model.dto;

import java.util.Date;

public class Message {
	private int messageId;
	private String sender;
	private String receiver;
	private String content;
	private Date sentDate;
	
	public Message() {}

	public Message(String sender, String receiver, String content) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	@Override
	public String toString() {
		return "Message [messageId=" + messageId + ", sender=" + sender + ", receiver=" + receiver + ", content="
				+ content + ", sentDate=" + sentDate + "]";
	}
	
}
