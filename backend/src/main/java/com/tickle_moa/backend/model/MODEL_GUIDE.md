# MODEL GUIDE (JWT 적용 전)

## 이 패키지의 역할
- DB 테이블과 매핑되는 도메인 모델

## 파일별 해석

### `User.java`
- 필드: `userId`, `name`, `email`, `password`, `createdAt`, `role`
- 매핑 테이블: `users`

### `Account.java`
- 필드: `accountId`, `userId`, `name`, `balance`, `createdAt`
- 매핑 테이블: `accounts`

### `Transaction.java`
- 필드: `tranId`, `accountId`, `type`, `category`, `amount`, `description`, `date`, `createdAt`
- 매핑 테이블: `transactions`

## 현재 구조 포인트
- Controller 요청/응답에 model을 직접 쓰는 구간이 있음
- JWT/보안 고도화 시 DTO 중심으로 분리하면 안전성과 유지보수가 좋아짐
