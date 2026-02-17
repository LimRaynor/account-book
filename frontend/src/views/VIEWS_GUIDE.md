# VIEWS GUIDE (JWT 적용 전)

## 이 패키지의 역할
- 실제 화면(UI)과 사용자 동작 처리

## 파일별 해석

### `Main.vue`
- 메인 페이지 제목 표시

### `AboutView.vue`
- 소개 페이지 정적 내용 표시

### `Signup.vue`
- `name/email/password` 입력
- `handleSignup()`에서 `POST /api/auth/signup` 호출

### `LoginView.vue`
- 이메일/비밀번호 입력
- `handleLogin()`에서 `POST /api/auth/login` 호출
- 성공 시 `authStore.setUser(res.data)`
- 이후 `/`로 이동

### `AccountView.vue`
- `onMounted` 시 로그인 사용자 기준 계좌 목록 조회
- 계좌 추가/삭제 후 목록 재조회

### `TransactionView.vue`
- `onMounted` 시 계좌 목록 조회
- 계좌 선택 시 거래 목록 조회
- 거래 추가/삭제 후 목록 재조회

## 사용자 흐름(현재)
1. 회원가입
2. 로그인
3. 계좌 등록/조회/삭제
4. 거래 등록/조회/삭제
