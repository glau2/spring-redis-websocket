package com.github.rawsanj.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AsyncConfig implements AsyncConfigurer {

	@Override
	public Executor getAsyncExecutor() {
		return new ConcurrentTaskExecutor(Executors.newFixedThreadPool(2));
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new CustomAsyncExceptionHandler();
	}

	static class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

		@Override
		public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {

			log.info("Exception message - {}", throwable.getMessage());
			log.info("Method name - {}", method.getName());
			for (Object param : obj) {
				log.info("Parameter value - {}", param);
			}
		}
	}
}
