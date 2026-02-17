# CONFIG GUIDE (JWT 적용 전)

## 이 패키지의 역할
- 백엔드 공통 설정을 담당합니다.
- 현재는 CORS 설정만 실제 동작하고, Security는 클래스 골격 상태입니다.

## 파일별 해석

### `SecurityConfig.java`
- 현재 내용: 빈 클래스
- 의미: Spring Security 필터 체인/비밀번호 인코더/인가 규칙이 아직 설정되지 않음
- 영향: JWT 인증 흐름이 아직 연결되지 않은 상태

### `WebConfig.java`
- `addCorsMappings()`에서 `/api/**` 경로에 대해 CORS 허용
- `allowedOrigins("http://localhost:5173")`
  - 프론트 개발 서버(Vite)에서 백엔드 API 호출 허용
- `allowedMethods("*")`, `allowedHeaders("*")`
  - 개발 단계에서 모든 메서드/헤더 허용

## 현재 흐름에서 위치
1. 브라우저에서 `http://localhost:5173` -> `http://localhost:8080/api/...` 호출
2. `WebConfig`의 CORS 정책 확인
3. 통과하면 Controller로 전달
