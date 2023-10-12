# 원티드 프리온보딩 백엔드 인턴십 과제

### 0. 기술 스택
- Java 11
- SpringBoot 2.7.16
- Spring Data JPA
- JUnit5
- Swagger 3.0.0
- MariaDB

### 1. 채용공고 등록
```
  {
    "companyName": "원티드",
    "position": "백엔드",
    "reward": 80000,
    "content": "신입을 뽑고있습니다",
    "stacks": [
      "MairaDB",
      "스프링",
      "자바"
    ]
  }
```
**구현**
- @PostMapping("/register")
- 회사와 포지션이 같으면 등록 안되게 설정
- 스택부분은 Set을 사용
- reward(보상금) 부분 빼고는 필수로 입력 받게 설정 

### 2. 채용공고 수정
```
  {
    "companyName": "원티드",
    "position": "백엔드",
    "reward": 10000,
    "content": "내용을 변경했습니다.",
    "stacks": [
      "MairaDB",
      "스프링",
      "자바"
    ]
  }
```
**구현**
- @PutMapping("/update/{companyPostId}")
- 삭제되지 않은 공고문만 수정 가능

### 3. 채용공고 삭제
- @DeleteMapping("/delete/{companyPostId}")
- 단순 구현을 위해 물리적 삭제로 진행(DB에서 데이터 삭제)
- 해당 공고가 없을 경우 예외처리
  
### 4. 채용공고 목록 
> 전체 공고 조회
```
[
  {
    "companyPostId": 1,
    "companyName": "원티드",
    "position": "백엔드",
    "reward": 80000,
    "content": "신입을 뽑고있습니다",
    "stacks": [
      "MairaDB",
      "스프링",
      "자바"
    ]
  },
  {
    "companyPostId": 2,
    "companyName": "인프런",
    "position": "프론트엔드",
    "reward": 90000,
    "content": "경력직을 뽑고 있습니다.",
    "stacks": [
      "자바 스크립트",
      "Next.js"
    ]
  },
  {
    "companyPostId": 3,
    "companyName": "온보딩",
    "position": "풀스택",
    "reward": 75000,
    "content": "신입 개발자 구합니다. 우리는 ...",
    "stacks": [
      "Node",
      "CI/CD",
      "AWS"
    ]
  }
...
]
```

**구현**
- @GetMapping("/list")
- DB에 저장된 채용 공고문을 전부 보여준다.
  
### 4-2. 채용공고 검색
> 회사명으로 검색
- @GetMapping("/list/company/{companyName}")
> 포지션으로 검색
- @GetMapping("/list/position/{position}")

### 5. 채용 상세 페이지
```
{
  "companyPostId": 3,
  "companyName": "온보딩",
  "position": "풀스택",
  "reward": 75000,
  "content": "신입 개발자 구합니다. 우리는 ...",
  "stacks": [
    "Node",
    "CI/CD",
    "AWS"
  ]
}
```

**구현**
- @GetMapping("/{companyPostId}/details")
- 공고문이 존재 하지 않을 경우 예외처리
- 해당 회사가 올린 다른 채용공고 표시 


### 6. 채용공고 지원
