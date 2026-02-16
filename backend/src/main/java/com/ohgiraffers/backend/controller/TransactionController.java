package com.ohgiraffers.backend.controller;

import com.ohgiraffers.backend.model.Transaction;
import com.ohgiraffers.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	// GET /api/transactions?accountId=1 - 거래 목록
	@GetMapping
	public ResponseEntity<List<Transaction>> getTransactions(@RequestParam Long accountId) {
		return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId));
	}

	// POST /api/transactions - 거래 추가
	@PostMapping
	public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction) {
		transactionService.createTransaction(transaction);
		return ResponseEntity.ok("거래 추가 성공");
	}

	// DELETE /api/transactions/5 - 거래 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
		transactionService.deleteTransaction(id);
		return ResponseEntity.ok("거래 삭제 성공");
	}
}
