package com.ohgiraffers.backend.controller;

import com.ohgiraffers.backend.model.User;
import com.ohgiraffers.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody User user) {
		authService.signup(user);
		return ResponseEntity.ok("회원가입 성공");
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		User found = authService.findByEmail(user.getEmail());
		if (found == null) {
			return ResponseEntity.badRequest().body("존재하지 않는 이메일입니다");
		}
		return ResponseEntity.ok("로그인 성공");
	}
}