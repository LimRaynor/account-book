# API GUIDE (JWT 적용 전)

## 이 패키지의 역할
- Axios 공통 인스턴스 제공

## 파일별 해석

### `axios.js`
- `axios.create({ baseURL: 'http://localhost:8080' })`
  - 모든 API 요청의 기본 서버 주소를 통일
- JWT 인터셉터 코드는 주석 상태
  - 즉, 현재 요청에는 Authorization 헤더 자동 첨부 없음

## 흐름
1. View/Store에서 `api` import
2. `api.get/post/delete` 호출
3. 백엔드 Controller로 전달
