# mongodb-demo

Spring Data MongoDB를 학습하기 위한 데모 프로젝트입니다. Member 도메인을 중심으로
임베디드 문서, 동적 쿼리, 배치 연산, 그리고 도메인 모델링(sealed class + Visitor 패턴)을 실습합니다.

## 학습 목적

MongoDB의 문서 지향 모델을 Spring Data MongoDB로 다루면서, 임베디드 문서 처리·페이징·
배열 수정 등 RDB와 다른 접근 방식을 학습합니다. 더불어 계층형 도메인 모델링과 일관된
API/예외/응답 구조를 적용해 봅니다.

## 사용 기술

- Java 17
- Spring Boot 4.0.5
- Spring Boot Starter Web
- Spring Boot Starter Data MongoDB
- Spring Boot Starter Validation
- springdoc-openapi (Swagger UI) 2.8.6
- Lombok
- Gradle (Groovy DSL)

## 다룬 주제 (코드·컨벤션 근거)

- **MongoDB Repository 구성** (`member/repository`)
  - `MongoRepository` + Custom 인터페이스(`MemberCustomRepository` / `...Impl`) 조합,
    Custom 구현체는 `MongoTemplate` 사용.
- **임베디드 문서 처리** (`MemberAddress`, `MemberHouse` 도메인 및 전용 Service/Controller)
  - 부모 문서에 내장된 객체(주소/주택)를 별도 엔드포인트(`/api/v1/{parent}/{parentId}/{sub}`)로 관리.
- **배열 수정 / 배치 연산**
  - `$` 위치 연산자, `$push`, `$pull`, `$elemMatch` 활용 및 다건 처리 시 `BulkOperations` 사용
    (CLAUDE.md 규약 기준).
- **페이징** — `PageableExecutionUtils.getPage()` lazy count, 임베디드 배열은 애플리케이션 레벨 subList 슬라이싱.
- **도메인 모델링 (Visitor 패턴 + sealed class)** (`member/domain`)
  - `Member`를 abstract sealed class로 두고 `MemberWithAddress` / `MemberWithHouse`로 계층 분리,
    `MemberVisitor<T>`로 `instanceof` 없이 타입별 처리. `MemberDtoMapper`가
    `MemberVisitor<MemberSummaryResponse>`를 구현. (커밋 메시지 기준)
  - `MongoConfig`에서 `initialEntitySet`으로 서브클래스 스캔, `@TypeAlias`로 `memberType` 매핑.
- **공통 응답 / 예외 처리** (`common`)
  - `ApiResult<T>`, `PageResponse`/`PageInfo` 응답 래퍼, `CustomException`/`ErrorCode`/
    `GlobalExceptionHandler` 기반 예외 처리, `BaseTimeEntity` auditing.
- **DTO** — 모든 DTO는 Java `record`, 응답 DTO는 `static from(Entity)` 팩토리, 검증 메시지 한국어.

> 패키지/네이밍/Repository/DTO/예외 규약의 상세 내용은 저장소 루트의 `CLAUDE.md`에 정리되어 있습니다.

## 프로젝트 구조

```
mongodb-demo/
├── CLAUDE.md                 # 프로젝트 컨벤션 문서
├── build.gradle
└── src/main/java/com/example/demo
    ├── DemoApplication.java
    ├── common/
    │   ├── domain/           # BaseTimeEntity
    │   ├── error/            # CustomException, ErrorCode, ErrorResponse, GlobalExceptionHandler
    │   └── response/         # ApiResult, PageResponse, PageInfo
    ├── config/MongoConfig.java
    └── member/
        ├── domain/           # Member(sealed), MemberWithAddress/House, MemberVisitor 등
        ├── application/      # MemberService, MemberAddressService, MemberHouseService, MemberDtoMapper
        ├── repository/       # MemberRepository + Custom(Impl), MemberHouseRepository(Impl)
        └── presentation/     # Controller + dto/in, dto/out
```
