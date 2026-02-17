# SERVICE GUIDE (JWT 적용 전)

## 이 패키지의 역할
- 비즈니스 로직 담당
- Controller와 Mapper 사이 중간 계층

## 파일별 해석

### `AuthService.java`
- 의존성
  - `UserMapper`
  - `BCryptPasswordEncoder`
- `signup(User user)`
  1. 이메일 중복 체크 (`findByEmail`)
  2. 비밀번호 BCrypt 암호화
  3. `role`을 `USER`로 설정
  4. DB 저장 (`insertUser`)
- `findByEmail(String email)`
  - 이메일로 사용자 조회

### `AccountService.java`
- `getAccountsByUserId(Long userId)`
  - 사용자 계좌 목록 조회
- `createAccount(Account account)`
  - 계좌 생성
- `deleteAccount(Long accountId)`
  - 계좌 삭제

### `TransactionService.java`
- `getTransactionsByAccountId(Long accountId)`
  - 계좌 거래 목록 조회
- `createTransaction(Transaction transaction)`
  - 거래 등록
- `deleteTransaction(Long tranId)`
  - 거래 삭제

## 흐름
1. Controller가 Service 호출
2. Service가 Mapper 호출
3. Mapper가 SQL 실행
