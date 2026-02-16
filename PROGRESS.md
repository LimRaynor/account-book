# 가계부 프로젝트 진행 현황

## 프로젝트 개요
- **목표**: 모놀리식 가계부 웹 애플리케이션
- **Backend**: Spring Boot 3.5 + MyBatis + Spring Security + MariaDB
- **Frontend**: Vue 3 + Pinia + Vue Router + Axios
- **베이스 패키지**: `com.ohgiraffers.backend`

## DB 스키마
| 테이블 | 설명 |
|--------|------|
| users | 회원 (user_id, name, email, password, created_at, role) |
| accounts | 계좌 (account_id, user_id, name, balance, created_at) |
| transactions | 거래내역 (tran_id, account_id, type, category, amount, description, date, created_at) |
| refresh_token | 리프레시 토큰 (user_id, refresh_token) |

## 개발 순서

### 1단계: 프로젝트 기반 세팅 (DB 없이)
- [ ] SecurityConfig 기본 설정 (CORS, CSRF, 허용 경로)
- [ ] 백엔드 패키지 구조 생성 (user, account, transaction)
- [ ] DTO 클래스 생성

### 2단계: 회원 기능 (DB 없이 - 하드코딩)
- [ ] UserController (signup, login API)
- [ ] UserService (임시 List 저장)
- [ ] 프론트 - Signup.vue 폼 구현
- [ ] 프론트 - Login.vue 페이지 생성
- [ ] 프론트 - userStore (Pinia)
- [ ] 프론트 - axios API 모듈

### 3단계: 계좌 기능 (DB 없이 - 하드코딩)
- [ ] AccountController (CRUD API)
- [ ] AccountService (임시 List 저장)
- [ ] 프론트 - AccountList.vue
- [ ] 프론트 - accountStore (Pinia)

### 4단계: 거래내역 기능 (DB 없이 - 하드코딩)
- [ ] TransactionController (CRUD API)
- [ ] TransactionService (임시 List + 잔액 연동)
- [ ] 프론트 - TransactionList.vue
- [ ] 프론트 - transactionStore (Pinia)

### 5단계: DB 연결 (마지막)
- [ ] MariaDB 연결 설정 (application.yml)
- [ ] MyBatis Mapper 인터페이스 + XML 작성
- [ ] Service에서 하드코딩 → Mapper 호출로 교체
- [ ] refresh_token 테이블 연동

## 현재 진행 상태
> **1단계 시작 전** — 프로젝트 기반 세팅 준비 중
