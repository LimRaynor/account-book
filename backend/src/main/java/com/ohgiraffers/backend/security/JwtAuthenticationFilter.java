package com.ohgiraffers.backend.security;

// 모든 HTTP 요청마다 실행되는 필터
// 1. 요청 헤더에서 "Authorization: Bearer {token}" 추출
// 2. 토큰이 유효하면 → SecurityContext에 인증 정보 저장
// 3. 토큰이 없거나 무효하면 → 그냥 통과 (SecurityConfig에서 차단)
public class JwtAuthenticationFilter {

}
