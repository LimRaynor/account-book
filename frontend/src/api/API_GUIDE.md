# API 패키지 (axios 설정)

## 왜 만들었나?
모든 화면에서 백엔드에 HTTP 요청을 보내야 하는데, 매번 `http://localhost:8080`을 쓰면 중복이다.
한 곳에서 axios 인스턴스를 만들어두고, 다른 파일에서 import해서 쓴다.

## 흐름에서의 위치
```
Vue 화면 (views)
     ↓ 함수 호출
Store (stores)
     ↓ api.get(), api.post() 등
axios 인스턴스 (api/axios.js) ← 여기
     ↓ HTTP 요청
백엔드 (localhost:8080)
```

## axios.create()를 쓴 이유
- 전역 axios를 직접 수정하면 다른 라이브러리에 영향을 줄 수 있음
- create()로 별도 인스턴스를 만들면 이 프로젝트 전용 설정만 적용됨

## baseURL을 설정한 이유
- 매번 `http://localhost:8080/api/accounts` 전체를 쓰지 않으려고
- baseURL을 설정하면 `/api/accounts`만 쓰면 됨

## export default로 내보낸 이유
- 이 파일에서 내보내는 건 인스턴스 하나뿐
- 다른 파일에서 `import api from '@/api/axios'`로 가져다 쓸 수 있음

## 인터셉터 주석을 남긴 이유
- 나중에 JWT 인증을 구현하면, 모든 요청에 토큰을 자동으로 붙여야 함
- 인터셉터가 그 역할을 하므로 자리만 확보해둔 것
