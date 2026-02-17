- security 패키지: 토큰 생성/검증, 필터, 인증 진입점
- 
- dto 패키지: 로그인 요청/토큰 응답 모델
- 
- config 패키지: SecurityFilterChain 설정, 필터 등록
- 
- service (또는 auth 로직): 로그인 검증, 사용자 조회
- 
- controller: 로그인/재발급 엔드포인트
- 
- (보통) repository/mapper + model: 사용자/리프레시토큰 저장

구조 흐름을 이해하면서 해보면좋을거같다
노션이랑 수업자료만봐서 아직흐름을 잘 모르겠다