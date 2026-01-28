# OrderFlow API - E-Commerce REST API

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)

> Spring Boot + JPA + QueryDSL + JWT 기반의 전자상거래 REST API 서버

## 프로젝트 소개

백엔드 개발을 공부하며 처음부터 끝까지 완성한 전자상거래 REST API 프로젝트입니다. JWT 인증, QueryDSL 동적 쿼리, 주문 관리 등 실무에서 필요한 핵심 기능을 직접 구현했습니다.

### 개발 동기

백엔드 개발자가 되기 위해 자바와 스프링을 처음 접하며 시작한 프로젝트입니다. 모든 용어가 낯설고 어디서부터 시작해야 할지 막막했지만, 하나씩 찾아보고 코드를 작성해가며 완성했습니다. 특히 내가 작성한 코드가 실제로 작동하고, API 응답이 정상적으로 나올 때의 성취감이 컸습니다.

### 주요 기능

- **회원 관리**: 회원가입, 로그인, JWT 토큰 기반 인증
- **상품 관리**: 상품 등록, 조회, 페이징, 동적 검색 (이름/가격 범위)
- **주문 관리**: 주문 생성, 취소, 재고 관리, 취소 이력 추적
- **보안**: Spring Security + JWT, 비밀번호 암호화
- **API 문서화**: Swagger/OpenAPI 3.0

### 개발 기간
- **2025.11.10 ~ 2025.12.10** (1개월)

## 기술 스택

### Backend
- **Framework**: Spring Boot 3.3.5
- **Language**: Java 21
- **ORM**: Spring Data JPA
- **Query**: QueryDSL 5.0.0
- **Security**: Spring Security, JWT (jjwt 0.11.5)
- **Database**: MySQL 8.0
- **Documentation**: Springdoc OpenAPI 2.6.0
- **Build Tool**: Gradle

### Architecture
- RESTful API 설계
- 계층형 아키텍처 (Controller - Service - Repository)
- DTO 패턴
- Global Exception Handler

---

## 개발 과정에서의 도전과 해결

### 도전 1: 자바와 스프링 용어의 벽
**문제**:
- 처음 접하는 자바 언어와 스프링 프레임워크
- Bean, IoC, DI 같은 개념들이 낯설었음
- 어노테이션(@)의 의미를 이해하기 어려웠음

**해결**:
- 모르는 용어가 나올 때마다 검색하고 노트 정리
- 공식 문서와 블로그를 반복해서 읽으며 이해
- 코드를 직접 작성하며 체득

### 도전 2: JPA 연관관계 설정
**문제**:
- Member, Product, Orders의 관계 설정
- N+1 문제 발생
- LazyInitializationException 에러

**해결**:
```java
// FetchType.LAZY 사용으로 N+1 문제 방지
@ManyToOne(fetch = FetchType.LAZY)
private Member member;

// 필요한 경우에만 join fetch 사용
```

### 도전 3: JWT 토큰 구현
**문제**:
- JWT가 무엇인지, 어떻게 작동하는지 이해 어려움
- 토큰을 어디에 저장하고 어떻게 검증할지 모름

**해결**:
- JWT의 구조(Header.Payload.Signature) 학습
- JwtUtil 클래스를 만들어 토큰 생성/검증 로직 분리
- Filter에서 토큰을 검증하고 SecurityContext에 저장

### 도전 4: QueryDSL 설정
**문제**:
- Gradle 설정이 복잡함
- Q클래스가 생성되지 않음

**해결**:
```gradle
// build.gradle에 QueryDSL 설정 추가
implementation "com.querydsl:querydsl-jpa:5.0.0:jakarta"
annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jakarta"
```

### 도전 5: API가 작동했을 때의 성취감
**순간**:
- Postman에서 회원가입 API를 호출했을 때 200 OK
- 내가 작성한 코드로 데이터베이스에 데이터가 저장됨
- JWT 토큰으로 인증이 성공하고 주문이 생성됨

이런 순간들이 계속 공부할 수 있는 원동력이 되었습니다.

---

## 개발하며 배운 것들

1. **Spring Boot 생태계 이해**
   - 의존성 주입(DI)과 제어의 역전(IoC)
   - Bean 라이프사이클과 스코프
   - 계층형 아키텍처의 중요성

2. **데이터베이스 관리**
   - JPA를 통한 객체-관계 매핑
   - 연관관계와 영속성 컨텍스트
   - 트랜잭션의 중요성

3. **보안의 기초**
   - 비밀번호 암호화의 필요성
   - JWT 토큰 기반 인증 방식
   - Spring Security의 필터 체인

4. **RESTful API 설계**
   - HTTP 메서드의 의미(GET, POST 등)
   - 적절한 상태 코드 사용
   - 일관된 응답 구조의 중요성

5. **문제 해결 능력**
   - 에러 메시지를 읽고 해석하는 법
   - 공식 문서와 스택오버플로우 활용
   - 디버깅 방법

---

## 핵심 구현 내용

### 1. JWT 인증/인가 시스템

```java
// JwtFilter.java - JWT 토큰 검증 필터
@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain) {
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        String username = jwtUtil.validateAndGetUsername(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
            );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
}
```

### 2. QueryDSL 동적 쿼리

```java
// ProductRepositoryImpl.java - 동적 검색 구현
@Override
public Page<Product> search(ProductSearchCond cond, Pageable pageable) {
    QProduct product = QProduct.product;
    BooleanBuilder builder = new BooleanBuilder();

    if (cond.name() != null && !cond.name().isBlank()) {
        builder.and(product.name.containsIgnoreCase(cond.name()));
    }
    if (cond.minPrice() != null) {
        builder.and(product.price.goe(cond.minPrice()));
    }
    if (cond.maxPrice() != null) {
        builder.and(product.price.loe(cond.maxPrice()));
    }

    List<Product> content = query
        .select(product)
        .from(product)
        .where(builder)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(QueryDslSortUtil.toOrderSpecifiers(pageable.getSort(), product))
        .fetch();

    long total = query
        .select(product.count())
        .from(product)
        .where(builder)
        .fetchOne();

    return new PageImpl<>(content, pageable, total);
}
```

### 3. 주문 취소 비즈니스 로직

```java
// OrderService.java - 주문 취소 처리
@Transactional
public OrderCancelResponse cancel(Long orderId, String username,
                                   OrderCancelRequest request) {
    // 1. 주문 조회
    Orders order = orderRepository.findById(orderId)
        .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

    // 2. 본인 주문 확인
    if (!order.getMember().getUsername().equals(username)) {
        throw new CustomException(ErrorCode.ORDER_UNAUTHORIZED);
    }

    // 3. 30분 이내 취소 확인
    if (order.getOrderAt().isBefore(LocalDateTime.now().minusMinutes(30))) {
        throw new CustomException(ErrorCode.ORDER_CANNOT_CANCEL);
    }

    // 4. 취소 가능 상태 확인
    order.validateCancelable();

    // 5. 주문 취소 처리
    order.cancel();

    // 6. 재고 복구
    order.getProduct().addStock(order.getQuantity());

    // 7. 취소 이력 저장
    OrderCancelHistory history = new OrderCancelHistory(
        order, username, request.reason()
    );
    cancelHistoryRepository.save(history);

    return OrderCancelResponse.from(order);
}
```

### 4. 재고 관리

```java
// Product.java - 재고 증감 로직
public void reduceStock(int quantity) {
    if (this.stock < quantity) {
        throw new CustomException(ErrorCode.OUT_OF_STOCK);
    }
    this.stock -= quantity;
}

public void addStock(int quantity) {
    this.stock += quantity;
}
```

### 5. 전역 예외 처리

```java
// GlobalExceptionHandler.java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResponse> handleCustom(CustomException ex) {
        ErrorCode code = ex.getErrorCode();
        return ResponseEntity
            .status(code.getStatus())
            .body(ApiErrorResponse.of(code));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValid(
        MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
            .getFieldError()
            .getDefaultMessage();
        return ResponseEntity
            .badRequest()
            .body(ApiErrorResponse.of("INVALID_INPUT", message, 400));
    }
}
```

---

## 프로젝트 구조

```
src/main/java/com/springboot/
├── config/              # 설정 클래스
│   ├── JwtFilter.java          # JWT 인증 필터
│   ├── SecurityConfig.java     # Spring Security 설정
│   ├── QuerydslConfig.java     # QueryDSL 설정
│   └── SwaggerConfig.java      # Swagger 설정
├── controller/          # REST API 컨트롤러
│   ├── MemberController.java   # 회원 API
│   ├── ProductController.java  # 상품 API
│   └── OrderController.java    # 주문 API
├── service/             # 비즈니스 로직
│   ├── MemberService.java
│   ├── ProductService.java
│   └── OrderService.java
├── repository/          # 데이터 접근 계층
│   ├── MemberRepository.java
│   ├── ProductRepository.java
│   ├── ProductRepositoryCustom.java      # QueryDSL 인터페이스
│   └── ProductRepositoryImpl.java        # QueryDSL 구현체
├── domain/              # 엔티티
│   ├── Member.java              # 회원 엔티티
│   ├── Product.java             # 상품 엔티티
│   ├── Orders.java              # 주문 엔티티
│   └── OrderCancelHistory.java # 주문 취소 이력
├── dto/                 # 데이터 전송 객체
│   ├── MemberRequest.java
│   ├── ProductRequest.java
│   ├── OrderRequest.java
│   └── ApiResponse.java         # 공통 응답 포맷
├── exception/           # 예외 처리
│   ├── CustomException.java           # 커스텀 예외
│   ├── ErrorCode.java                 # 에러 코드 정의
│   └── GlobalExceptionHandler.java    # 전역 예외 처리
└── util/                # 유틸리티
    └── JwtUtil.java             # JWT 토큰 생성/검증
```

## 데이터베이스 구조 (ERD)

```
┌─────────────────┐         ┌─────────────────┐
│     Member      │         │    Product      │
├─────────────────┤         ├─────────────────┤
│ id (PK)         │         │ id (PK)         │
│ username (UK)   │         │ name            │
│ password        │         │ price           │
│ name            │         │ stock           │
│ email           │         │ created_at      │
│ role            │         └─────────────────┘
│ created_at      │                  ▲
└─────────────────┘                  │
         │                           │
         ▼                           │
┌─────────────────────────────────┐  │
│          Orders                 │  │
├─────────────────────────────────┤  │
│ id (PK)                         │  │
│ member_id (FK) ─────────────────┘  │
│ product_id (FK) ───────────────────┘
│ quantity                        │
│ total_price                     │
│ status                          │
│ order_at                        │
│ canceled_at                     │
└─────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────┐
│   OrderCancelHistory            │
├─────────────────────────────────┤
│ id (PK)                         │
│ order_id (FK)                   │
│ canceled_by                     │
│ cancel_reason                   │
│ cancel_at                       │
└─────────────────────────────────┘
```

---

## API 명세

### 회원 API

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/members` | 회원가입 | No |
| POST | `/members/login` | 로그인 (JWT 발급) | No |
| GET | `/members` | 회원 목록 조회 | Yes |

**회원가입 요청 예시**
```json
{
  "username": "user01",
  "password": "1234",
  "name": "홍길동",
  "email": "test@test.com"
}
```

**로그인 응답 예시**
```json
{
  "success": true,
  "data": "eyJhbGciOiJIUzI1NiJ9...",
  "error": null
}
```

### 상품 API

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/products` | 상품 등록 | Yes |
| GET | `/products` | 상품 목록 조회 (페이징) | Yes |
| GET | `/products/search` | 상품 검색 (이름/가격) | Yes |

**상품 검색 예시**
```bash
GET /products/search?name=아이폰&minPrice=500000&maxPrice=2000000
```

### 주문 API

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/orders` | 주문 생성 | Yes |
| GET | `/orders` | 내 주문 목록 조회 | Yes |
| POST | `/orders/{id}/cancel` | 주문 취소 | Yes |
| GET | `/orders/cancel/history` | 주문 취소 이력 조회 | Yes |

**주문 생성 요청 예시**
```json
{
  "productId": 2,
  "quantity": 2
}
```

## API 응답 형식

### 성공 응답
```json
{
  "success": true,
  "data": {
    "orderId": 1,
    "productName": "갤럭시 s20",
    "quantity": 2,
    "totalPrice": 2200000,
    "orderAt": "2025-12-30T12:00:00",
    "status": "CREATED"
  },
  "error": null
}
```

### 에러 응답
```json
{
  "success": false,
  "code": "OUT_OF_STOCK",
  "message": "재고가 부족합니다.",
  "status": 400,
  "timestamp": "2025-12-30T12:00:00"
}
```

## 보안 구현

1. **비밀번호 암호화**
   - BCryptPasswordEncoder 사용
   - 단방향 해시 암호화로 안전하게 저장

2. **JWT 토큰 인증**
   - Stateless 인증 방식
   - Access Token (1시간 유효)
   - Bearer 토큰 헤더 방식

3. **인가 처리**
   - Spring Security를 통한 URL 기반 접근 제어
   - 회원가입/로그인만 permitAll, 나머지는 인증 필요

4. **SQL Injection 방지**
   - JPA와 QueryDSL의 Prepared Statement 사용

---

## 시작하기

### 필수 요구사항

- Java 21 이상
- MySQL 8.0 이상
- Gradle 8.x

### 설치 및 실행

1. **저장소 클론**
```bash
git clone https://github.com/dooseok913/spring-jwt-shop-restapi.git
cd spring-jwt-shop-restapi
```

2. **데이터베이스 생성**
```sql
CREATE DATABASE learning1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. **application.properties 수정**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/learning1?useSSL=false&serverTimezone=Asia/Seoul
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

4. **애플리케이션 실행**
```bash
./gradlew bootRun
```

5. **API 문서 확인**
```
http://localhost:8080/swagger-ui.html
```

---

## 향후 개선 계획

- [ ] **테스트 코드 작성**: JUnit 5, MockMvc를 활용한 단위/통합 테스트
- [ ] **Redis 캐싱**: 상품 조회 API 성능 개선
- [ ] **동시성 제어**: 재고 관리에 낙관적/비관적 락 적용
- [ ] **Docker 환경**: Docker Compose로 개발 환경 표준화
- [ ] **CI/CD 구축**: GitHub Actions 자동 배포
- [ ] **로깅 개선**: Logback 설정으로 체계적인 로그 관리
- [ ] **API 버전 관리**: v1, v2 등 버전별 API 관리

## 라이센스

This project is licensed under the MIT License.

## 개발자

**최두석**
- 동국대학교 경제학과 졸업 (2010.02)
- Email: cds1745@naver.com
- GitHub: [@dooseok913](https://github.com/dooseok913)

---

> "처음에는 모든 게 낯설고 어려웠지만, 하나씩 배워가며 완성한 프로젝트입니다. 코드가 작동하고 API 응답이 나올 때마다 느끼는 성취감이 저를 계속 앞으로 나아가게 했습니다."
