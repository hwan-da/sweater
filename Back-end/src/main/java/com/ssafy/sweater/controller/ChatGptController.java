package com.ssafy.sweater.controller;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.sweater.model.dto.gpt.AiMessage;
import com.ssafy.sweater.model.dto.gpt.Body;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatGptController {
 
    private String key = "api-key";
 
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ChatGptController(RestTemplate restTemplate, ObjectMapper objectMapper) {
    	this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }
   
    @PostMapping("/send")
    public ResponseEntity<String> sendChatMessage(@RequestParam("message") String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + key);
        
        Body body = new Body("gpt-3.5-turbo", Collections.singletonList(new AiMessage("user", message)));
        HttpEntity<Body> requestEntity = new HttpEntity<>(body, headers);
        
        //System.out.println("여기선 뭘.. 하냐..?");
        
        return restTemplate.postForEntity(url, requestEntity, String.class);
    }

}