package com.tickle_moa.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration        // 이 클래스는 설정 파일이야
@EnableWebSecurity    // Spring Security 활성화
public class SecurityConfig {

	// 비밀번호 암호화 도구 (AuthService에서 @Autowired로 주입됨)
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 보안 규칙 설정
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())              // CSRF 비활성화 (REST API니까)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/**").permitAll()  // 회원가입/로그인은 누구나 OK
				.anyRequest().permitAll()              // 나머지도 일단 허용 (개발 중)
			);

		return http.build();
	}
}