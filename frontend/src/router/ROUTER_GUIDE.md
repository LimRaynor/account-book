# ROUTER GUIDE (JWT 적용 전)

## 이 패키지의 역할
- URL과 View 매핑

## 파일별 해석

### `index.js`
- `/` -> `Main.vue`
- `/about` -> `AboutView.vue`
- `/signup` -> `Signup.vue`
- `/login` -> `LoginView.vue`
- `/accounts` -> `AccountView.vue`
- `/accounts/create` -> `AccountView.vue`
- `/transactions` -> `TransactionView.vue`

## 현재 포인트
- 라우트 가드는 아직 없음
- JWT 적용 전이라 로그인 여부 기반 페이지 차단 로직이 없음
