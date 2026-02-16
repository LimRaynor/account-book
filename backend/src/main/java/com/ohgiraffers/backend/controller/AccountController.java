package com.ohgiraffers.backend.controller;

import com.ohgiraffers.backend.model.Account;
import com.ohgiraffers.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	// GET /api/accounts?userId=1 - 내 계좌 목록
	@GetMapping
	public ResponseEntity<List<Account>> getAccounts(@RequestParam Long userId) {
		return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
	}

	// POST /api/accounts - 계좌 생성
	@PostMapping
	public ResponseEntity<String> createAccount(@RequestBody Account account) {
		accountService.createAccount(account);
		return ResponseEntity.ok("계좌 생성 성공");
	}

	// DELETE /api/accounts/3 - 계좌 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
		accountService.deleteAccount(id);
		return ResponseEntity.ok("계좌 삭제 성공");
	}
}