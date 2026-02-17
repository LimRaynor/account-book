# Router 패키지 (라우팅)

## 왜 만들었나?
Vue는 SPA(Single Page Application) — HTML 파일은 index.html 하나뿐이다.
URL이 바뀌어도 페이지 새로고침 없이 화면만 교체해야 하므로, Router가 "URL → 화면 컴포넌트" 매핑을 담당한다.

## 흐름에서의 위치
```
사용자가 링크 클릭 or URL 직접 입력
     ↓
Router (여기) ← URL 분석 → 어떤 View를 보여줄지 결정
     ↓
해당 View 컴포넌트 렌더링
     ↓
App.vue의 <RouterView/> 자리에 표시
```

## 경로 목록

| path | name | component | 설명 |
|------|------|-----------|------|
| / | home | Main.vue | 메인 페이지 |
| /about | about | AboutView.vue | 소개 페이지 |
| /signup | signup | Signup.vue | 회원가입 |
| /login | login | LoginView.vue | 로그인 |
| /accounts | accounts | AccountView.vue | 계좌 관리 |
| /transactions | transactions | TransactionView.vue | 거래 내역 |

## createWebHistory를 쓴 이유
- URL이 `/accounts` 처럼 깔끔하게 보임 (HTML5 History API)
- 다른 옵션인 createWebHashHistory는 `/#/accounts` 처럼 `#`이 붙음

## lazy loading (화살표 함수 import)을 쓴 이유
```js
// 즉시 로딩 — 앱 시작할 때 전부 불러옴
component: Main

// 지연 로딩 — 해당 페이지에 갈 때만 불러옴
component: () => import('../views/LoginView.vue')
```
- Main.vue만 즉시 로딩 (첫 화면이니까)
- 나머지는 지연 로딩 → 초기 로딩 속도 향상

## App.vue와의 관계
```
App.vue
├── <nav> (항상 보임 — RouterLink들)
└── <RouterView/> (여기에 현재 URL에 맞는 View가 들어감)
```
- `<RouterLink to="/login">` → 클릭하면 URL을 /login으로 변경
- `<RouterView/>` → Router가 /login에 매핑된 LoginView.vue를 이 자리에 렌더링

## router.push()를 쓴 이유 (LoginView 등)
```js
router.push('/')
```
- 코드에서 프로그래밍적으로 페이지 이동
- 사용자가 링크를 클릭한 것과 동일한 효과
- 로그인 성공 후 메인 페이지로 자동 이동할 때 사용
