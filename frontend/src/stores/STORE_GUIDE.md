# Stores 패키지 (Pinia 상태관리)

## 왜 만들었나?
로그인한 유저 정보, 계좌 목록, 거래 내역을 여러 화면에서 공유해야 한다.
각 화면(Vue 컴포넌트)이 따로 데이터를 가지면 동기화가 안 되므로, 중앙 저장소(Store)에서 관리한다.

## 흐름에서의 위치
```
Vue 화면 (views)
     ↓ store.함수() 호출
Store (여기) ← 데이터 보관 + API 호출
     ↓ api.get(), api.post()
axios → 백엔드
```
- 화면은 store의 함수만 호출하면 됨
- API 호출 로직은 store 안에 캡슐화

## 3개 store 역할

| store | 보관하는 데이터 | 하는 일 |
|-------|----------------|---------|
| auth.js | 로그인한 유저 정보 (user) | setUser, clearUser |
| account.js | 계좌 목록 (accounts) | fetchAccounts, addAccount, deleteAccount |
| transaction.js | 거래 내역 (transactions) | fetchTransactions, addTransaction, deleteTransaction |

## defineStore('이름', () => {})를 쓴 이유
- Composition API 스타일 — ref(), function을 그대로 사용 가능
- Options API 스타일(state, actions, getters)보다 직관적
- `<script setup>`에서 쓰는 방식과 동일해서 일관성 있음

## ref([])로 배열을 선언한 이유
- Vue의 반응형 시스템 — ref 안의 값이 바뀌면 화면이 자동으로 갱신됨
- 일반 변수(`let accounts = []`)로 하면 화면이 안 바뀜

## API 호출을 store에 넣은 이유
- 화면에서는 `store.fetchAccounts(userId)` 한 줄로 끝남
- API URL, 파라미터 조합 등의 세부사항을 화면이 알 필요 없음
- 백엔드 Service 패키지와 비슷한 역할 (판단 + API 호출)

## fetch 후 res.data를 ref에 넣는 이유
```
const res = await api.get('/api/accounts', { params: { userId } })
accounts.value = res.data
```
- axios 응답은 `{ data, status, headers, ... }` 구조
- 실제 데이터는 `res.data` 안에 있음
- `.value`는 ref의 실제 값에 접근하는 방법

## auth store가 다른 store와 다른 점
- API 호출 없이 데이터만 보관 (setUser/clearUser)
- 로그인은 LoginView에서 직접 API 호출 후, 응답을 store에 저장하는 방식
- 다른 화면에서 `authStore.user.userId`로 유저 ID를 꺼냄
