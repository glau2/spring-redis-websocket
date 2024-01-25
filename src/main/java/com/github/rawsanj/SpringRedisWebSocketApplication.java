package com.github.rawsanj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

import com.github.rawsanj.config.ApplicationProperties;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableConfigurationProperties(ApplicationProperties.class)
public class SpringRedisWebSocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisWebSocketApplication.class, args);
	}

}
