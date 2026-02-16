# Config 패키지

## 왜 만들었나?
Spring Boot의 기본 동작을 우리 프로젝트에 맞게 커스터마이징하는 설정 파일들.
설정 없이는 Security가 모든 요청을 차단하고, CORS 에러가 발생함.

## 흐름에서의 위치
```
서버 시작 시 → Config 클래스들이 먼저 로딩됨
                ↓
요청이 들어오면 → SecurityConfig 규칙에 따라 허용/차단 판단
                ↓
허용된 요청만 → Controller로 전달
```

[요청 시작]
↓
[Filter 1: CORS 필터] (우리 편 서버인지 확인)
↓
[Filter 2: Security 필터] (로그인했는지, 권한 있는지 확인)
↓
[Filter 3: 기타 필터] (글자가 깨지지 않게 인코딩 등)
↓
[Controller] (드디어 실제 기능 실행)

## SecurityConfig.java

### @Configuration + @EnableWebSecurity를 붙인 이유
- @Configuration: Spring에게 "이건 설정 파일이야"라고 알려줌
- @EnableWebSecurity: Spring Security 기능 활성화

### @Bean passwordEncoder()를 만든 이유
- 비밀번호를 평문으로 DB에 저장하면 보안 위험
- BCryptPasswordEncoder가 비밀번호를 암호화해줌
- @Bean으로 등록해야 AuthService에서 @Autowired로 주입받을 수 있음
- "1234" → "$2a$10$xJ8k..." 이런 식으로 변환됨

### csrf.disable()한 이유
- CSRF는 폼 기반 웹사이트를 위한 보안
- REST API는 JWT 토큰으로 인증하니까 CSRF가 불필요

### permitAll()을 쓴 이유
- 개발 중이라 모든 요청 허용
- 나중에 JWT 인증 추가하면 특정 경로만 허용으로 변경 예정

## WebConfig.java

### CORS 설정이 필요한 이유
- 브라우저는 보안상 다른 포트/도메인 요청을 차단함 (Same-Origin Policy)
- 프론트(5173) → 백엔드(8080)는 포트가 다르니까 차단됨
- CORS 설정으로 "이 주소는 허용해"라고 명시해야 함
