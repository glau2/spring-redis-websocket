package com.github.rawsanj.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rawsanj.model.ChatMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MessageController {

	private final AtomicInteger idCounter = new AtomicInteger(1);
	private final StringRedisTemplate stringRedisTemplate;

	public MessageController(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@MessageMapping("/message")
	public void sendWsChatMessage(String message) throws JsonProcessingException {
		log.info("Incoming WebSocket Message : {}", message);

		publishMessageToRedis(message);
	}

	@PostMapping("/message")
	public ResponseEntity<Map<String, String>> sendHttpChatHttpMessage(@RequestBody Map<String, String> message)
			throws JsonProcessingException {
		String httpMessage = message.get("message");
		log.info("Incoming HTTP Message : {}", httpMessage);
		publishMessageToRedis(httpMessage);

		Map<String, String> response = new HashMap<>();
		response.put("response", "Message Sent Successfully over HTTP");

		return ResponseEntity.ok(response);
	}

	private void publishMessageToRedis(String message) throws JsonProcessingException {

		Integer totalChatMessage = idCounter.incrementAndGet();
		String hostName = null;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			hostName = "localhost";
		}

		ChatMessage chatMessage = new ChatMessage(totalChatMessage, message, hostName);
		ObjectMapper objectMapper = new ObjectMapper();
		String chatString = objectMapper.writeValueAsString(chatMessage);

		// Publish Message to Redis Channels
		stringRedisTemplate.convertAndSend("chat", chatString);

	}
}
