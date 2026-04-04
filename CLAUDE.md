# CLAUDE.md

## Package Structure

```
{root}/
├── common/
│   ├── domain/          # BaseTimeEntity (auditing)
│   ├── error/           # CustomException, ErrorCode, ErrorResponse, GlobalExceptionHandler
│   └── response/        # ApiResult, PageResponse, PageInfo
├── config/
└── {feature}/           # 도메인별 패키지
    ├── domain/          # 엔티티, 임베딩 객체
    ├── application/     # Service
    ├── repository/      # Repository
    └── presentation/    # Controller
        └── dto/
            ├── in/      # 요청 DTO
            └── out/     # 응답 DTO
```

## Naming Conventions

- Controller: `{Feature}Controller` / 임베딩 관련: `{Parent}{Sub}Controller`
- Service: `{Feature}Service` / 임베딩 관련: `{Parent}{Sub}Service`
- Repository: `{Feature}Repository` + `{Feature}CustomRepository` + `{Feature}{Sub}Repository`
- 요청 DTO: `Create{Feature}Request`, `Update{Feature}Request`, `{Feature}SearchRequest`
- 응답 DTO: `{Feature}Response`, `{Feature}SummaryResponse`

## API URL Rules

- 기본: `/api/v1/{resource}`
- 임베딩: `/api/v1/{parent}/{parentId}/{sub}`
- 다건 수정/삭제: URL에 개별 ID 없이 `List` body로 배치 처리

## Controller Rules

- 모든 엔드포인트는 `ApiResult<T>`로 감싸서 반환
- 메서드 순서: 전체조회 → 상세조회 → 등록 → 수정 → 삭제
- 페이징 파라미터는 `@ParameterObject Pageable` 또는 커스텀 SearchRequest 사용
- `@Valid @RequestBody`로 요청 검증
- Swagger: `@Tag(name = "XX. 기능명")` (번호로 순서 지정)

## Service Rules

- `@RequiredArgsConstructor`로 의존성 주입
- 비즈니스 예외는 `throw new CustomException(ErrorCode.XXX)`
- ErrorCode는 static import 사용
- `@Transactional`은 여러 쓰기 작업이 있을 때만 사용 (단건 수정에는 불필요)
- Repository는 결과(modifiedCount 등)만 반환, 예외는 Service에서 처리

## Repository Rules (MongoDB)

- `MongoRepository` + Custom 인터페이스 조합으로 구성
- Custom 구현체는 `MongoTemplate` 사용
- 페이징: `PageableExecutionUtils.getPage()`로 lazy count
- 배열 수정: `$` 위치 연산자, `$push`, `$pull`, `$elemMatch`
- 다건 수정: `BulkOperations` 사용 (Service 반복 호출 대신)
- 다건 삭제: `$pull` + `$or`로 한 번에 처리

## DTO Rules

- 모든 DTO는 Java `record` 사용
- 응답 DTO에 `static from(Entity)` 팩토리 메서드 제공
- Validation 메시지는 한국어
- SearchRequest는 `toPageable()` 메서드 포함 (기본값: page=0, size=20, sortBy=createdAt, direction=DESC)

## Domain Rules

- 최상위 엔티티: `@Document`, `@Id`, `BaseTimeEntity` 상속
- 임베딩 객체: `@Getter`, `@NoArgsConstructor`, `@AllArgsConstructor` (Lombok)
- 임베딩 객체 네이밍: 부모 엔티티 접두사
- 임베딩 객체의 복합 키는 수정 불가 — 수정 시 비키 필드만 변경
- 임베딩 배열 페이징: 애플리케이션 레벨 subList 슬라이싱
- Setter 금지 — `update()` 메서드로 변경
- `@Data` 금지 — `@Getter` + 필요한 생성자 어노테이션만 사용
- 컬렉션 필드 초기화: `new ArrayList<>()`

## Error Handling

- `ErrorCode` enum: `HttpStatus` + 한국어 메시지
- `CustomException` extends `RuntimeException` wrapping `ErrorCode`
- `GlobalExceptionHandler`에서 `CustomException`, `MethodArgumentNotValidException`, `Exception` 처리
- `ErrorResponse`: `@JsonPropertyOrder({"success", "message", "code", "timestamp"})`

## Common Response

- 단건/다건: `ApiResult<T>` — `{ success: true, data: T }`
- 페이징: `ApiResult<PageResponse<T>>` — `{ success: true, data: { list: [], pageInfo: {} } }`
- 에러: `ErrorResponse` — `{ success: false, code: "", message: "", timestamp: "" }`
