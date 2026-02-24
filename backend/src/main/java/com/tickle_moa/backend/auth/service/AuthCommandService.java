package com.tickle_moa.backend.auth.service;

import com.tickle_moa.backend.auth.dto.LoginRequest;
import com.tickle_moa.backend.auth.dto.TokenResponse;
import com.tickle_moa.backend.auth.entity.RefreshToken;
import com.tickle_moa.backend.auth.repository.AuthRepository;
import com.tickle_moa.backend.user.command.entity.User;
import com.tickle_moa.backend.user.command.repository.UserRepository;
import com.tickle_moa.backend.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;

    /* 로그인 */
    public TokenResponse login(LoginRequest request) {

        // 1. email로 조회 -> email, pwd(암호화) 조회됨
        User user
                = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다"));

        // 2. 비밀번호 매칭 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다");
        }

        // 3. 비밀번호 일치 -> 로그인 성공 -> 토큰 발급
        String accessToken
                = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
        String refreshToken
                = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRole().name());

        // 4. RefreshToken 저장(보안 및 토큰 재발급 검증용)
        RefreshToken tokenEntity = RefreshToken.builder()
                .userId(user.getUserId())
                .token(refreshToken)
                .expiryDate(
                        new Date(System.currentTimeMillis()
                                + jwtTokenProvider.getRefreshExpiration())
                ).build();

        authRepository.save(tokenEntity);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /* 토큰 재발급 */
    @Transactional
    public TokenResponse refresh(String refreshTokenValue) {

        // 1. refreshToken 유효성 검사
        if (!jwtTokenProvider.validateToken(refreshTokenValue)) {
            throw new BadCredentialsException("유효하지 않은 Refresh Token 입니다");
        }

        // 2. DB에서 refreshToken 조회
        RefreshToken storedToken = authRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new BadCredentialsException("존재하지 않는 Refresh Token 입니다"));

        // 3. refreshToken 만료 여부 확인
        if (storedToken.getExpiryDate().before(new Date())) {
            authRepository.delete(storedToken);
            throw new BadCredentialsException("만료된 Refresh Token 입니다");
        }

        // 4. refreshToken에서 email 추출 후 사용자 조회
        String email = jwtTokenProvider.getEmailFromJWT(refreshTokenValue);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("사용자를 찾을 수 없습니다"));

        // 5. 새로운 accessToken, refreshToken 발급
        String newAccessToken
                = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
        String newRefreshToken
                = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRole().name());

        // 6. 기존 refreshToken 삭제 후 새 refreshToken 저장
        authRepository.delete(storedToken);

        RefreshToken newTokenEntity = RefreshToken.builder()
                .userId(user.getUserId())
                .token(newRefreshToken)
                .expiryDate(
                        new Date(System.currentTimeMillis()
                                + jwtTokenProvider.getRefreshExpiration())
                ).build();

        authRepository.save(newTokenEntity);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    /* 로그아웃 (토큰 소멸) */
    @Transactional
    public void logout(String refreshTokenValue) {

        // 1. DB에서 refreshToken 조회 후 삭제
        authRepository.findByToken(refreshTokenValue)
                .ifPresent(authRepository::delete);
    }

}
