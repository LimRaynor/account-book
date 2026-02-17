# ASSETS GUIDE

## 이 패키지의 역할
- 전역 스타일/정적 리소스 관리

## 파일별 해석

### `base.css`
- 공통 변수/기본 스타일 정의

### `main.css`
- 앱 전역 스타일 진입점

### `logo.svg`
- 정적 이미지 리소스

## 적용 흐름
- `src/main.js`에서 `import './assets/main.css'`
- 앱 전체에 스타일 적용
