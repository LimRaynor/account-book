package com.tickle_moa.backend.auth.service;

import com.tickle_moa.backend.auth.dto.LoginRequest;
import com.tickle_moa.backend.auth.dto.TokenResponse;
import com.tickle_moa.backend.auth.entity.RefreshToken;
import com.tickle_moa.backend.auth.repository.AuthRepository;
import com.tickle_moa.backend.jwt.JwtTokenProvider;
import com.tickle_moa.backend.user.command.entity.User;
import com.tickle_moa.backend.user.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;

    /* 로그인 */
    public TokenResponse login(LoginRequest request) {

        // 1. email로 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다"));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다");
        }

        // 3. 토큰 발급
        String accessToken = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRole().name());

        // 4. RefreshToken DB 저장
        RefreshToken tokenEntity = RefreshToken.builder()
                .email(user.getEmail())
                .token(refreshToken)
                .expiryDate(new Date(System.currentTimeMillis() + jwtTokenProvider.getRefreshExpiration()))
                .build();

        authRepository.save(tokenEntity);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /* refresh token 검증 후 새 토큰 발급 */
    public TokenResponse refreshToken(String provideRefreshToken) {

        // 리프레시 토큰 유효성 검증
        jwtTokenProvider.validateToken(provideRefreshToken);

        // 토큰에서 email 추출
        String email = jwtTokenProvider.getUsernameFromJWT(provideRefreshToken);

        // DB에서 email로 리프레시 토큰 조회
        RefreshToken storedToken = authRepository.findById(email)
                .orElseThrow(() -> new BadCredentialsException("해당 유저로 조회되는 리프레시 토큰 없음"));

        // 요청 토큰과 DB 저장 토큰 일치 확인
        if (!storedToken.getToken().equals(provideRefreshToken)) {
            throw new BadCredentialsException("리프레시 토큰이 일치하지 않음");
        }

        // 만료 기간 확인
        if (storedToken.getExpiryDate().before(new Date())) {
            throw new BadCredentialsException("리프레시 토큰 유효 기간 만료");
        }

        // 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("해당 유저 없음"));

        // 새 토큰 발급
        String accessToken = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRole().name());

        // RefreshToken 갱신 (PK 동일 → UPDATE)
        RefreshToken tokenEntity = RefreshToken.builder()
                .email(email)
                .token(refreshToken)
                .expiryDate(new Date(System.currentTimeMillis() + jwtTokenProvider.getRefreshExpiration()))
                .build();

        authRepository.save(tokenEntity);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /* DB refreshToken 삭제 */
    public void logout(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);
        String email = jwtTokenProvider.getUsernameFromJWT(refreshToken);
        authRepository.deleteById(email);
    }
}
