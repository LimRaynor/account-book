# Views 패키지 (페이지 화면)

## 왜 만들었나?
사용자가 실제로 보는 화면 단위. 각 URL 경로(/, /login, /accounts 등)에 1:1로 대응된다.
백엔드의 Controller가 "요청 접수 역할"이라면, View는 "화면 표시 + 사용자 입력 수집 역할"이다.

## 흐름에서의 위치
```
브라우저 URL 변경
     ↓
Router (경로 매칭)
     ↓
View (여기) ← 화면 렌더링 + 사용자 이벤트 처리
     ↓ store 함수 호출
Store → axios → 백엔드
```

## 4개 화면 역할

| 화면 | URL | 하는 일 |
|------|-----|---------|
| Signup.vue | /signup | 이름, 이메일, 비밀번호 입력 → 회원가입 API |
| LoginView.vue | /login | 이메일, 비밀번호 입력 → 로그인 API → store에 유저 저장 |
| AccountView.vue | /accounts | 계좌 목록 조회 + 추가 + 삭제 |
| TransactionView.vue | /transactions | 계좌 선택 → 거래 목록 조회 + 추가 + 삭제 |

## `<script setup>`을 쓴 이유
- Vue 3의 Composition API 단축 문법
- return 없이 선언한 변수/함수가 template에서 바로 사용 가능
- 코드가 짧고 직관적

## ref()로 입력값을 선언한 이유
- `const email = ref('')` → input의 v-model과 양방향 바인딩
- 사용자가 입력하면 ref 값이 바뀌고, ref 값을 바꾸면 input에 반영됨

## @submit.prevent를 쓴 이유
- form 태그의 기본 동작: 제출 시 페이지 새로고침
- `.prevent`가 새로고침을 막고, 우리가 정의한 함수를 실행시킴
- 백엔드의 @RequestBody처럼 "데이터를 모아서 보내는" 역할

## onMounted()를 쓴 이유 (AccountView, TransactionView)
- 페이지가 열릴 때 자동으로 데이터를 불러오기 위함
- "화면 뜨자마자 계좌 목록 조회" 같은 초기 로딩에 사용
- Signup, Login은 조회할 게 없어서 안 씀

## v-for로 목록을 그린 이유
```html
<li v-for="account in accountStore.accounts" :key="account.accountId">
```
- 배열을 순회하면서 `<li>`를 반복 생성
- `:key`는 Vue가 각 항목을 구분하기 위한 고유값 (변경 감지 최적화)

## v-if를 쓴 이유 (TransactionView)
```html
<form v-if="selectedAccountId" ...>
```
- 계좌를 선택하지 않았으면 거래 추가 폼을 숨김
- 조건부 렌더링 — 조건이 true일 때만 DOM에 존재

## try/catch를 쓴 이유
- API 요청은 네트워크 에러, 서버 에러(400, 500) 등으로 실패할 수 있음
- try: 정상 흐름 (성공 시 실행)
- catch: 에러 흐름 (실패 시 alert 표시)

## LoginView에서 authStore.setUser(res.data)를 하는 이유
- 로그인 응답으로 User 객체(userId, name, email)가 옴
- 이걸 store에 저장해야 다른 화면(계좌, 거래)에서 userId를 쓸 수 있음
- 저장 안 하면 "누가 로그인했는지" 알 수 없음
