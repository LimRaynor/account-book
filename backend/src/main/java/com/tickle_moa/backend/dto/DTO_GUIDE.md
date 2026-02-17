# DTO GUIDE (JWT 적용 전)

## 이 패키지의 역할
- API 요청/응답 전용 객체를 두는 위치

## 파일별 해석

### `LoginRequest.java`
- 현재: 필드 없는 빈 클래스
- 의도: 로그인 요청(email/password) DTO 자리

### `SignupRequest.java`
- 현재: 필드 없는 빈 클래스
- 의도: 회원가입 요청(name/email/password) DTO 자리

### `TokenResponse.java`
- 현재: 필드 없는 빈 클래스
- 의도: JWT 도입 후 accessToken/refreshToken 응답 DTO 자리

## 현재 상태 요약
- DTO가 아직 실사용되지 않고, Controller는 `model.User`를 직접 받는 구조
