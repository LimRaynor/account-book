# STORE GUIDE (JWT 적용 전)

## 이 패키지의 역할
- Pinia 상태 관리

## 파일별 해석

### `auth.js`
- 상태: `user`
- 액션
  - `setUser(userData)`: 로그인 성공 시 사용자 저장
  - `clearUser()`: 로그아웃 시 사용자 초기화

### `account.js`
- 상태: `accounts`
- 액션
  - `fetchAccounts(userId)`
  - `addAccount(account)`
  - `deleteAccount(id)`

### `transaction.js`
- 상태: `transactions`
- 액션
  - `fetchTransactions(accountId)`
  - `addTransaction(transaction)`
  - `deleteTransaction(id)`

## 흐름
1. View 이벤트 발생
2. Store action 실행
3. API 호출
4. Store state 갱신
5. UI 자동 반영
