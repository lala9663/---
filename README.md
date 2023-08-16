## 지원자: 김태윤

### Index
1. [실행 방법](#실행-방법)
2. [테이블 구조](#테이블-구조)
3. [데모 영상](#데모-영상)
4. [구현 방법 및 이유](#구현-방법-및-이유)
5. [API 명세서](#api-명세서)
### 실행 방법

### 테이블 구조
![erd](https://github.com/lala9663/wanted-pre-onboarding-backend/assets/105348713/c9cfd82a-3118-401d-88d4-9e95d2012e33)

### 데모 영상
[데모 영상](https://youtu.be/A3xH2YtrMq4)

### 구현 방법 및 이유

### api-명세서
1. 회원가입  
- 요청
  - URL: '/members/signup'
  - 메서드: POST
  - 요청 본문:
    ```json
    {
      "email" : "lala9663@naver.com",
      "password" : "xodbs2050",
      "phone" : "01066142040"
    }
    ```
- 응답:
  - 성공
    ```json
    {
      "message": "회원가입이 성공했습니다."
    }
    ```
  - 실패
    ```json
    {
      "error": "이미 존재하는 회원입니다."
    }
    ```
    ```json
    {
      "error": "유효하지 않은 이메일 형식입니다."
    }
    ```
    ```json
    {
      "error": "비밀번호는 숫자와 영문자를 포함한 8자 이상이어야 합니다."
    }
    
2. 로그인
- 요청:
  - URL: `/members/login`
  - 메서드: POST
  - 요청 본문:
    ```json
    {
      "email" : "lala9663@naver.com",
      "password" : "xodbs2050"
    }
    ```
- 응답:
  - 성공
    ```json
    {
      "massage": "로그인에 성공했습니다.",
    "data": {
        "grantType": "Bearer",
        "accessToken": " ",
        "refreshToken": " ",
        "refreshTokenExpirationTime": 604800000
        }
    }
    ```
  - 실패
    ```json
    {
      "error": "비밀번호가 일치하지 않습니다."
    }
    ```

3. 새로운 게시글 생성
- 요청:
  - URL: `/board`
  - 메서드: POST
  - Authorization: Bearer {accessToken}
  - 요청 본문:
    ```json
    {
      "title": "게시글 제목",
      "content": "게시글 내용"
    }
    ```
- 응답:
  - 성공
    ```json
    {
      "massage": "로그인에 성공했습니다.",
    "data": {
        "grantType": "Bearer",
        "accessToken": "엑세스토큰 값",
        "refreshToken": "리프레시토큰 값",
        "refreshTokenExpirationTime": 604800000
        }
    }
    ```
  - 실패
    ```json
    {
      "error": "게시글 제목은 최대 100자까지 입력 가능합니다."
    }
    ```
    ```json
    {
      "error": "로그인을 해주세요"
    }
    ```

4.  게시글 목록 조회
- 요청:
  - URL: `/board/paged?page=0&size=10`
  - 메서드: GET
- 응답:
  - 성공
    ```json
    {
    "content": [
        {
            "boardId": 1,
            "title": "게시글 제목",
            "content": "게시글 내용"
        },
        {
            "boardId": 2,
            "title": "게시글 제목",
            "content": "게시글 내용"
        },
        {
            "boardId": 3,
            "title": "게시글 제목",
            "content": "게시글 내용"
        },
        {
            "boardId": 4,
            "title": "게시글 제목1",
            "content": "게시글 내용"
        },
        {
            "boardId": 5,
            "title": "게시글 제목2",
            "content": "게시글 내용"
        },
        {
            "boardId": 6,
            "title": "게시글 제목65",
            "content": "게시글 내용"
        },
        {
            "boardId": 7,
            "title": "원티드5",
            "content": "게시글 내용"
        },
        {
            "boardId": 8,
            "title": "원티드5",
            "content": "이번 잼버리는 폭염, 위생, 벌레 등의 문제가 속출하면서 파행을 겪었다....."
        },
        {
            "boardId": 9,
            "title": "상태코드",
            "content": "클래스의 상태 코드는 클라이언트가 요청한 동작을 수신하여 이해했고 승낙했으며...."
        },
        {
            "boardId": 10,
            "title": "상태코드400",
            "content": "서버가 요청의 구문을 인식하지 못했다. 401(권한 없음): 이 요청은 인증이 필요하다...."
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageSize": 10,
        "pageNumber": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 10,
    "totalPages": 1,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 10,
    "empty": false
    }
    ```

5. 특정 게시글 조회
- 요청:
  - URL: `/board/{boardId}`
  - 메서드: GET
- 응답:
  - 성공
    ```json
     {
    "boardId": 10,
    "title": "상태코드400",
    "content": "서버가 요청의 구문을 인식하지 못했다. 401(권한 없음): 이 요청은 인증이 필요하다..."
    }
    ```
  - 실패
    ```json
    {
      "error": "해당 게시물이 없습니다."
    }
    ```

6. 게시글 수정
- 요청:
  - URL: `/board/{boardId}`
  - 메서드: PUT
  - Authorization: Bearer {accessToken}
- 응답:
  - 성공
    ```json
    {
      "message" : "수정되었습니다"
    }
    ```
  - 실패
    ```json
    {
      "error": "해당 게시물이 없습니다."
    }
    ```
    ```json
    {
      "error": "권한이 없습니다."
    }
    ```

7. 게시글 삭제
- 요청:
  - URL: `/board/{boardId}/delete`
  - 메서드: PUT
  - Authorization: Bearer {accessToken}
- 응답:
  - 성공
    ```json
    {
      "message" : "삭제되었습니다."
    }
    ```
  - 실패
    ```json
    {
      "error": "해당 게시물이 없습니다."
    }
    ```
    ```json
    {
      "error": "삭제 권한이 없습니다."
    }
    ```

8. 토큰 재발급
- 요청:
  - URL: `/members/reissue`
  - 메서드: POST
  - 요청 본문:
    ```json
    {
     "accessToken": "엑세스 토큰",
     "refreshToken": "리프레시 토큰"
    }
    ```
- 응답:
  - 성공
    ```json
    "massage": "Token 정보가 갱신되었습니다.",
    "data": {
        "grantType": "Bearer",
        "accessToken": "엑세스 토큰",
        "refreshToken": "리프레시 토큰",
        "refreshTokenExpirationTime": 604800000
    }
    ```
  - 실패
    ```json
    {
      "error": "만료된 토큰입니다."
    }
    ```
