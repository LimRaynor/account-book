# MAPPER GUIDE (JWT 적용 전)

## 이 패키지의 역할
- MyBatis 인터페이스 정의
- 실제 SQL은 `resources/mappers/*.xml`에서 실행

## 파일별 해석

### `UserMapper.java`
- `findByEmail(String email)`
- `insertUser(User user)`
- XML 연결: `UserMapper.xml`

### `AccountMapper.java`
- `findByUserId(Long userId)`
- `insertAccount(Account account)`
- `deleteById(Long accountId)`
- XML 연결: `AccountMapper.xml`

### `TransactionMapper.java`
- `findByAccountId(Long accountId)`
- `insertTransaction(Transaction transaction)`
- `deleteById(Long tranId)`
- XML 연결: `TransactionMapper.xml`

## 흐름
1. Service에서 Mapper 인터페이스 메서드 호출
2. MyBatis가 같은 id를 가진 XML SQL 실행
3. 결과를 model 객체로 매핑해서 반환
