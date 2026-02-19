package com.tickle_moa.backend.user.command.controller;

import com.tickle_moa.backend.common.ApiResponse;
import com.tickle_moa.backend.user.command.dto.UserCreateRequest;
import com.tickle_moa.backend.user.command.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserCommandController {

    private final UserCommandService userCommandService;

    /* 회원 가입 - USER 권한 */
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<Void>> register(
            @RequestBody UserCreateRequest userCreateRequest
    ) {
        userCommandService.registUser(userCreateRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(null));
    }

    /* 회원 가입 - ADMIN 권한 */
    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<Void>> registerAdmin(
            @RequestBody UserCreateRequest userCreateRequest
    ) {
        userCommandService.registAdmin(userCreateRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(null));
    }
}
