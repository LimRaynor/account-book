package com.ohgiraffers.backend.config;

// Spring Security 설정
// - /api/auth/** 경로는 인증 없이 허용
// - 나머지 경로는 JWT 인증 필요
// - CSRF 비활성화 (REST API이므로)
// - CORS 허용
public class SecurityConfig {

}
