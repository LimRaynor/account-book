# Controller 패키지

## 왜 만들었나?
프론트엔드(Vue)가 보낸 HTTP 요청을 받아서, Service에게 넘기고, 결과를 JSON으로 응답하는 입구 역할.

## 흐름에서의 위치
```
Vue (axios로 HTTP 요청)
       ↓
Controller (요청 접수 + 응답 반환)
       ↓
Service (비즈니스 로직)
       ↓
Mapper → DB
```

## @RestController를 붙인 이유
- @Controller + @ResponseBody의 합체
- 메서드가 반환하는 객체를 자동으로 JSON으로 변환해서 응답

## @RequestMapping을 붙인 이유
- 이 컨트롤러의 모든 API 경로에 공통 접두사를 붙임
- "/api/auth" → 이 안의 메서드들은 전부 /api/auth/... 로 시작

## HTTP 메서드 어노테이션
- @GetMapping → 조회 (데이터 가져올 때)
- @PostMapping → 생성 (데이터 보낼 때)
- @DeleteMapping → 삭제

## 파라미터 받는 3가지 방법
- @RequestBody → JSON 본문을 Java 객체로 변환 (POST에서 사용)
- @RequestParam → URL 뒤의 ?key=value에서 값 추출 (GET에서 사용)
- @PathVariable → URL 경로의 /{id}에서 값 추출 (DELETE에서 사용)

```
POST   /api/auth/signup     body: {"email":"a@b.com"}  → @RequestBody
GET    /api/accounts?userId=1                           → @RequestParam
DELETE /api/accounts/3                                  → @PathVariable
```

## ResponseEntity를 쓴 이유
- HTTP 상태코드(200, 400 등)와 응답 본문을 함께 반환할 수 있음
- ResponseEntity.ok("성공") → 200 + "성공"
- ResponseEntity.badRequest().body("에러") → 400 + "에러"
