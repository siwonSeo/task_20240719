## 개발환경
- Java 21
- spring boot (3.3.0)
- JPA
- Gradle
- H2
- Swagger

## API소개
직원 입력
POST /api/employee
아래의 3케이스로 서비스 메서드 정의
-파일 업로드(csv,json에 따라 다르게 파싱)
-body json입력
-body csv입력(text로 받아서 파싱)


직원 목록 조회
GET /api/employee

직원 조회
GET /api/employee/{name}