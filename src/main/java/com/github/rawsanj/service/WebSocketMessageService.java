package com.github.rawsanj.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.rawsanj.config.ApplicationProperties;
import com.github.rawsanj.model.ChatMessage;

@Service
public class WebSocketMessageService {

	private final ApplicationProperties applicationProperties;
	private final SimpMessagingTemplate template;

	public WebSocketMessageService(ApplicationProperties applicationProperties, SimpMessagingTemplate template) {
		this.applicationProperties = applicationProperties;
		this.template = template;
	}

	@Async
	public void sendChatMessage(ChatMessage message) {
		template.convertAndSend(applicationProperties.getTopic().getMessage(), message);
	}

}
