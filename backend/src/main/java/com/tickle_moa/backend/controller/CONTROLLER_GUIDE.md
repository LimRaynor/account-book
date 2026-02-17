# CONTROLLER GUIDE (JWT 적용 전)

## 이 패키지의 역할
- HTTP 요청 진입점
- 요청 데이터 수신 -> Service 호출 -> 응답 반환

## 파일별 해석

### `AuthController.java`
- `POST /api/auth/signup`
  - `@RequestBody User user`를 받아 `authService.signup(user)` 호출
  - 성공 시 200 OK 문자열 응답
- `POST /api/auth/login`
  - 이메일로 사용자 조회 후, 있으면 `User` 객체 그대로 반환
  - 없으면 `400 Bad Request`
- 주의: 현재 로그인 시 비밀번호 검증 로직이 Controller에 없음

### `AccountController.java`
- `GET /api/accounts?userId=...`
  - 특정 유저 계좌 목록 조회
- `POST /api/accounts`
  - 계좌 생성
- `DELETE /api/accounts/{id}`
  - 계좌 삭제

### `TransactionController.java`
- `GET /api/transactions?accountId=...`
  - 계좌별 거래 목록 조회
- `POST /api/transactions`
  - 거래 등록
- `DELETE /api/transactions/{id}`
  - 거래 삭제

## 요청 흐름
1. Frontend axios 호출
2. Controller endpoint 매칭
3. Service 메서드 호출
4. 결과를 JSON/문자열로 응답
