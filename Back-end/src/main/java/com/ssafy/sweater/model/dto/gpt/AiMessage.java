package com.ssafy.sweater.model.dto.gpt;

public class AiMessage {
	private String role;
	private String content;
	
	public AiMessage() {}

	public AiMessage(String role, String content) {
		this.role = role;
		this.content = content;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Message [role=" + role + ", content=" + content + "]";
	}
	
}
