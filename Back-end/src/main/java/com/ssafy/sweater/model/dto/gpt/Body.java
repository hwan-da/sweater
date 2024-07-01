package com.ssafy.sweater.model.dto.gpt;

import java.util.List;

public class Body {
    String model;
    List<AiMessage> messages;
    
	public Body() {}

	public Body(String model, List<AiMessage> messages) {
		this.model = model;
		this.messages = messages;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<AiMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<AiMessage> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "Body [model=" + model + ", messages=" + messages + "]";
	}
    
}