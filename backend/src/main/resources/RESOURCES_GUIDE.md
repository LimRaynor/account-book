# RESOURCES GUIDE (JWT 적용 전)

## 이 패키지의 역할
- 환경설정(yml)과 SQL(XML) 보관

## 파일별 해석

### `application.yml`
- datasource
  - url: `jdbc:mariadb://localhost:3306/account_book_db`
  - username/password: `account` / `book`
- mybatis
  - mapper-locations: `classpath:mappers/**/*.xml`
  - map-underscore-to-camel-case: true
- server
  - port: `8080`

### `mappers/UserMapper.xml`
- `findByEmail`
- `insertUser`

### `mappers/AccountMapper.xml`
- `findByUserId`
- `insertAccount`
- `deleteById`

### `mappers/TransactionMapper.xml`
- `findByAccountId`
- `insertTransaction`
- `deleteById`

## 주의할 점
- `application.yml`의 `type-aliases-package` 값이 과거 패키지명(`com.ohgiraffers.backend`)으로 남아 있음
- 현재 Java 패키지는 `com.tickle_moa.backend`이므로 정합성 점검 필요
