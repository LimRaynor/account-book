package com.ohgiraffers.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")           // /api/로 시작하는 모든 경로
			.allowedOrigins("http://localhost:5173")  // Vue 개발서버 허용
			.allowedMethods("*")  // 모든 HTTP 메서드 허용
			.allowedHeaders("*");            // 모든 헤더 허용
	}
}