# 우아한 테크 캠프 Pro - 미션 3

## 미션명 : ATDD(인수 테스트 주도 개발)

### # 1단계 : 지하철 노선도 관리

#### 요구 사항

- 지하철 노선 관리 기능을 구현하기
    - 기능 목록: 생성 / 목록 조회 / 조회 / 수정 / 삭제
    - 기능 구현 전 인수 테스트 작성
    - 기능 구현 후 인수 테스트 리팩터링
    
#### 단계별 요구사항 

**1.지하철 노선 관련 기능의 인수 테스트를 작성하기**
    - LineAcceptanceTest 를 모두 완성시키세요.
```java
@DisplayName("지하철 노선 관련 기능")
public class LineAcceptanceTest extends AcceptanceTest {
    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createLine() {
        // when
        // 지하철_노선_생성_요청

        // then
        // 지하철_노선_생성됨
    }

    ...
}
```


**2.지하철 노선 관련 기능 구현하기**
    - 인수 테스트가 모두 성공할 수 있도록 LineController를 통해 요청을 받고 처리하는 기능을 구현하세요.
```java
@RestController
@RequestMapping("/lines")
public class LineController {

    ...

	@PostMapping
	public ResponseEntity createLine(@RequestBody LineRequest lineRequest) {
		// TODO
	}

	@GetMapping
	public ResponseEntity<List<LineResponse>> findAllLines() {
		// TODO
	}
    
    ...
}
```
**3.인수 테스트 리팩터링**
인수 테스트의 각 스텝들을 메서드로 분리하여 재사용하세요.
ex) 인수 테스트 요청 로직 중복 제거 등# 우아한 테크 캠프 Pro - 미션 3

## 미션명 : ATDD(인수 테스트 주도 개발)

### # 1단계 : 지하철 노선도 관리

#### 요구 사항

- 지하철 노선 관리 기능을 구현하기
    - 기능 목록: 생성 / 목록 조회 / 조회 / 수정 / 삭제
    - 기능 구현 전 인수 테스트 작성
    - 기능 구현 후 인수 테스트 리팩터링

#### 단계별 요구사항

**1.지하철 노선 관련 기능의 인수 테스트를 작성하기**
- LineAcceptanceTest 를 모두 완성시키세요.
```java
@DisplayName("지하철 노선 관련 기능")
public class LineAcceptanceTest extends AcceptanceTest {
    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createLine() {
        // when
        // 지하철_노선_생성_요청

        // then
        // 지하철_노선_생성됨
    }

    ...
}
```


**2.지하철 노선 관련 기능 구현하기**
- 인수 테스트가 모두 성공할 수 있도록 LineController를 통해 요청을 받고 처리하는 기능을 구현하세요.
```java
@RestController
@RequestMapping("/lines")
public class LineController {

    ...

	@PostMapping
	public ResponseEntity createLine(@RequestBody LineRequest lineRequest) {
		// TODO
	}

	@GetMapping
	public ResponseEntity<List<LineResponse>> findAllLines() {
		// TODO
	}
    
    ...
}
```
**3.인수 테스트 리팩터링**
인수 테스트의 각 스텝들을 메서드로 분리하여 재사용하세요.
ex) 인수 테스트 요청 로직 중복 제거 등


### # 2단계 : 인수 테스트 리팩터링

#### 요구 사항

1.노선 생성 시 종점역(상행, 하행) 정보를 요청 파라미터에 함께 추가하기

- 두 종점역은 **구간**의 형태로 관리되어야 함

```java
public class LineRequest {
    private String name;
    private String color;
    private Long upStationId;       // 추가
    private Long downStationId;     // 추가
    private int distance;           // 추가
    ...
}
```



2.노선 객체에서 구간 정보를 관리하기

- 노선 생성시 전달되는 두 종점역은 노선의 상태로 관리되는 것이 아니라 구간으로 관리되어야 함

```java
public class Line {
    ...
    private List<Section> sections;
    ...
}
```



3.노선의 역 목록을 조회하는 기능 구현하기

- 노선 조회 시 역 목록을 함께 응답할 수 있도록 변경
- 노선에 등록된 구간을 순서대로 정렬하여(상행역 부터 하행역 순) 상행 종점부터 하행 종점까지 목록을 응답하기
- 필요시 노선과 구간(혹은 역)의 관계를 새로 맺기