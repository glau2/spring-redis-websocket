package com.github.rawsanj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "raw", ignoreUnknownFields = false)
@Getter
@Setter
public class ApplicationProperties {

	private Topic topic = new Topic();

	@Getter
	@Setter
	public static class Topic {
		private String message;
		private String count;
	}

}
