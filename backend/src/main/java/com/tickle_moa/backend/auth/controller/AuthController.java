package com.tickle_moa.backend.auth.controller;

import com.tickle_moa.backend.auth.dto.LoginRequest;
import com.tickle_moa.backend.auth.dto.TokenResponse;
import com.tickle_moa.backend.auth.service.AuthService;
import com.tickle_moa.backend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @RequestBody LoginRequest loginRequest
    ) {
        TokenResponse response = authService.login(loginRequest);

        return buildTokenResponse(response);
    }

    /* refresh token으로 요청 시 전달 받아
     * 유효한 토큰이면 새 access/refresh token 발급해서 반환 */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        // 쿠키에 refresh token이 없을 경우
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        /* refresh token에 문제가 없다면 새 access/refresh token 발급 */
        TokenResponse tokenResponse = authService.refreshToken(refreshToken);

        return buildTokenResponse(tokenResponse);
    }

    /* 로그아웃 */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        // refreshToken이 존재할 경우 == 로그인 상태
        if (refreshToken != null) {
            authService.logout(refreshToken); // DB refreshToken 삭제
        }

        ResponseCookie deleteCookie = createDeleteRefreshTokenCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(ApiResponse.success(null));
    }

    /* accessToken 및 refreshToken을 body와 쿠키에 담아 반환 */
    private ResponseEntity<ApiResponse<TokenResponse>> buildTokenResponse(TokenResponse tokenResponse) {
        ResponseCookie cookie = createRefreshTokenCookie(tokenResponse.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success(tokenResponse));
    }

    /* refreshToken 쿠키 생성 */
    private ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)                     // HttpOnly 속성 설정 (JavaScript 접근 불가)
                // .secure(true)                    // HTTPS 환경에서만 전송 (운영 환경에서 활성화 권장)
                .path("/")                          // 어디서나
                .maxAge(Duration.ofDays(7))         // 7일유효
                .sameSite("Strict")                 // CSRF 공격 방어를 위한 SameSite 설정
                .build();
    }

    /* refreshToken 쿠키 삭제 설정 */
    private ResponseCookie createDeleteRefreshTokenCookie() {
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                // .secure(true)
                .path("/")
                .maxAge(0)                          // 생성되자마자 0초라 바로 자동으로사라짐
                .sameSite("Strict")
                .build();
    }
}
