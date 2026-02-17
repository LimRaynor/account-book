# Tickle-Moa (가계부 Account Book)

모놀리식 개인 재무 관리 웹 애플리케이션

## 기술 스택

| 구분 | 기술                                             |
|------|------------------------------------------------|
| **Backend** | Spring Boot 3.5.10 + MyBatis + Spring Security |
| **Frontend** | Vue 3 + Vite + Pinia + Axios                   |
| **Database** | MariaDB 10.11 (account_book_db)                |
| **언어** | Java 21, JavaScript                             |
| **빌드** | Gradle (백엔드), Vite (프론트엔드)                     |

## 프로젝트 구조

```
account-book/
├── backend/                            ← Spring Boot 서버
│   ├── src/main/java/.../backend/
│   │   ├── controller/                 API 입구 (요청 접수 + 응답)
│   │   ├── service/                    비즈니스 로직 (판단 + 가공)
│   │   ├── mapper/                     MyBatis 인터페이스 (메서드 선언)
│   │   ├── model/                      DB ↔ Java 데이터 그릇 (VO)
│   │   ├── dto/                        요청/응답 전용 객체
│   │   ├── config/                     Security, CORS 설정
│   │   └── security/                   JWT 인증
│   ├── src/main/resources/
│   │   ├── application.yml             DB 연결 + MyBatis + 서버 설정
│   │   └── mappers/                    MyBatis SQL (XML)
│   └── build.gradle
│
├── frontend/                           ← Vue 3 클라이언트
│   ├── src/
│   │   ├── views/                      페이지 컴포넌트
│   │   ├── components/                 재사용 컴포넌트
│   │   ├── stores/                     Pinia 상태 관리
│   │   ├── api/                        Axios HTTP 클라이언트
│   │   └── router/                     Vue Router
│   └── package.json
│
├── ARCHITECTURE.md                     시스템 아키텍처 설계
├── MVP_GUIDE.md                        MVP 개발 가이드
├── LIBRARY_TRACKER.md                  라이브러리 문서
└── PROGRESS.md                         개발 진행 현황
```

## 요청 흐름

```
[Vue 프론트엔드] → axios로 HTTP 요청 (localhost:5173)
       ↓
[Controller]   → 요청 접수, 응답 반환 (@RestController)
       ↓
[Service]      → 비즈니스 로직 - 판단 + 가공 (@Service)
       ↓
[Mapper]       → 인터페이스 - 메서드 선언 (@Mapper)
       ↓
[Mapper XML]   → 실제 SQL 실행 (<select>, <insert>, <delete>)
       ↓
[MariaDB]      → 데이터 저장/조회 (localhost:3306)
```

## DB 테이블

| 테이블 | 설명 |
|--------|------|
| users | 회원 (user_id, name, email, password, created_at, role) |
| accounts | 계좌 (account_id, user_id, name, balance, created_at) |
| transactions | 거래내역 (tran_id, account_id, type, category, amount, description, date, created_at) |
| untitled | 리프레시 토큰 (user_id, refresh_token) |

## API 엔드포인트

| 메서드 | 경로 | 설명 |
|--------|------|------|
| POST | `/api/auth/signup` | 회원가입 |
| POST | `/api/auth/login` | 로그인 |
| GET | `/api/accounts?userId=1` | 계좌 목록 |
| POST | `/api/accounts` | 계좌 생성 |
| DELETE | `/api/accounts/{id}` | 계좌 삭제 |
| GET | `/api/transactions?accountId=1` | 거래 목록 |
| POST | `/api/transactions` | 거래 추가 |
| DELETE | `/api/transactions/{id}` | 거래 삭제 |

## 상세 가이드

| 문서 | 내용 |
|------|------|
| [백엔드 README](backend/README.md) | 패키지별 역할, 코딩 순서, 어노테이션 설명 |
| [PROGRESS.md](PROGRESS.md) | 개발 진행 현황 (완료/남은 작업) |
| [ARCHITECTURE.md](ARCHITECTURE.md) | 전체 시스템 아키텍처 |
| [MVP_GUIDE.md](MVP_GUIDE.md) | MVP 단계별 개발 가이드 |

## 실행 방법

```bash
# 백엔드 (http://localhost:8080)
cd backend && ./gradlew bootRun

# 프론트엔드 (http://localhost:5173)
cd frontend && npm run dev
```

## DB 접속 정보

| 항목 | 값 |
|------|---|
| 호스트 | localhost:3306 |
| DB명 | account_book_db |
| 계정 | account / book |

## �׽�Ʈ ���� ���� (1 ~ 5)

### 1. ȸ������
![1. ȸ������](test/1.ȸ������test.png)

### 2. �α���
![2. �α���](test/2.%20�α���test.png)

### 3. ���� ���
![3. ���� ���](test/3.���µ��test.png)

### 4. �ŷ�����
![4. �ŷ�����](test/4.�ŷ�����test.png)
![4-1. �ŷ����� ���](test/4_1.�ŷ��������test.png)

### 5. DB ��� Ȯ��
![5. DB ��� Ȯ��](test/5.DB���Ȯ��test.png)
