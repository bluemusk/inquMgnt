# inquMgnt
## 고객 문의 접수 및 답변 

## 1. Requirements
* 고객이 문의를 등록 / 답변 내용 확인
* 상담사가 등록된 문의가 있는지 확인
* 상담사는 문의에 대한 담당자 지정 후 답변
* 고객/상담사 페이지 분리
* 고객 페이지 : 문의 목록 조회 / 문의 작성
* 상담사 페이지 : 상담사 로그인 / 미답변 목록 조회 / 답변 작성


## 2. Problem Solving
### 1) Development environment
* Language : **Java 17**
* FrameWork : **Spring Boot 2.7.2.RELEASE + Spring JPA + Junit 5**
* Database : **H2 2.1.214.RELEASE** 

    ``` 
    datasource:
        * url: jdbc:h2:~/kakaopay
        * username: genie
        * password: wlsl22
        * driver-class-name: org.h2.Driver
    ```
### 2) Database Modeling
* **TABLE 및 DDL**
    * **TB_USER : 사용자 테이블** 
    * **TB_INQU : 문의/답변 내역 저장 테이블**
    * **TB_CLSER : 상담원 정보 저장 테이블**
``` H2 Database

    create table tb_clser (
       clser_id varchar(255) not null,
        clser_nm varchar(255) not null,
        input_dthms timestamp not null,
        pwd varchar(255) not null,
        primary key (clser_id)
    )

    
    create table tb_inqu (
       inqu_id bigint not null,
        asmt_dthms timestamp,
        clser_id varchar(255),
        cmpl_yn varchar(255) default 'N',
        input_dthms timestamp not null,
        inqu_cn varchar(255) not null,
        inqu_titl varchar(255) not null,
        rpl_cn varchar(255),
        rpl_dthms timestamp,
        user_id varchar(255) not null,
        primary key (inqu_id)
    )

    
    create table tb_user (
       user_id varchar(255) not null,
        input_dthms timestamp not null,
        primary key (user_id)
    )
```          

* ERD

 <img width="560" height="315" src='erd.png'>
 

## 3. API Common Spec
### 1) 공통 응답
* 응답코드
  `HTTP 응답코드`

| 응답코드 | 설명                  |
| -------- | --------------------- |
| `200` | **정상 응답**         |
| `201` | **정상적으로 생성**         |
| `400`    | 잘못된 요청           |
| `404`    | 리소스를 찾을 수 없음 |
| `500`    | 시스템 에러           |

  ```json
  // 예시
  {
    "errorStatus": "INTERNAL_SERVER_ERROR",
    "errorCode": 500,
    "errorMessage": "예상하지 못한 에러가 발생하였습니다."
  }
  ```

`에러코드 및 메시지`


| 에러코드 | 메시지                  |
| -------- | --------------------- |
| `E0000` | 예상치 못한 오류가 발생하였습니다.         |
| `E0101`    |중복된 ID가 존재합니다          |
| `E0102`    | 로그인 정보를 잘못 입력하였습니다. |
| `E0103`    | 데이터가 존재하지 않습니다          |
| `E0104`    | 해당ID로 등록된 문의가 없습니다.           |


## 4. 상세 API 스펙 및 제약사항 처리
### 문의 내역 조회 전 유저id 존재여부 체크 API
---

#### 요청

| 항목 | 값                    | 설명 |
| ---- |----------------------| --- |
| URL  | `GET` /kakaopay/user |

| 이름        |   타입    | 필수 | 설명    |
|-----------|:-------:| :---: |-------|
| user_id   | String  |  ○   | 사용자id |


#### 응답

`응답 내용`

| 이름 |   타입    | 필수 | 설명        |
| ---- |:-------:| :---: |-----------|
| body   | String  |  ○   | 중복ID 존재여부 |

`응답 예시`

```
중복된 ID가 없습니다. 문의 내역 입력 가능
```


### 문의 내역 저장 API
---

#### 요청

| 항목 | 값                     | 설명 |
| ---- |-----------------------| --- |
| URL  | `POST` /kakaopay/inqu |

| 이름        |   타입    | 필수 | 설명    |
|-----------|:-------:| :---: |-------|
| user_id   | String  |  ○   | 사용자id |
| inqu_titl | String  |  ○   | 문의 제목 |
| inqu_cn   | String  |  ○   | 문의 내용 |


#### 응답

`응답 내용`

| 이름 |   타입    | 필수 | 설명      |
| ---- |:-------:| :---: |---------|
| body   | String  |  ○   | 저장 성공여부 |

`응답 예시`

```
문의 내역이 저장되었습니다
```

### 문의 내역 조회 API
---

#### 요청

| 항목 | 값                    | 설명 |
| ---- |----------------------| --- |
| URL  | `GET` /kakaopay/inqu |

| 이름        |   타입    | 필수 | 설명    |
|-----------|:-------:| :---: |-------|
| user_id   | String  |  ○   | 사용자id |


#### 응답

`응답 내용`

| 이름 |   타입    | 필수 | 설명   |
| ---- |:-------:| :---: |------|
| body   | String  |  ○   | 문의내역 |

`응답 예시`

```
[{"inqu_id":1,
"user_id":"8090666",
"inqu_titl":"결제문의",
"inqu_cn":"2번 결제됨",
"input_dthms":"2022-08-04 05:49:33"}
,{"inqu_id":2,
"user_id":"8090666",
"inqu_titl":"택배",
"inqu_cn":"택배가 안와요",
"input_dthms":"2022-08-04 05:49:53"}]
```

### 같은 사용자id로 여러건의 문의 내역이 있을떄  내역 조회 API
---

#### 요청

| 항목 | 값                       | 설명 |
| ---- |-------------------------| --- |
| URL  | `GET` /kakaopay/inquOne |

| 이름      |   타입    | 필수 | 설명    |
|---------|:-------:| :---: |-------|
| inqu_id | String  |  ○   | 사용자id |


#### 응답

`응답 내용`

| 이름 |   타입    | 필수 | 설명   |
| ---- |:-------:| :---: |------|
| body   | String  |  ○   | 문의내역 |

`응답 예시`

```
[{"inqu_id":1,
"user_id":"8090666",
"inqu_titl":"결제문의",
"inqu_cn":"2번 결제됨",
"input_dthms":"2022-08-04 05:49:33"}
]
```

### 상담원 시스템 등록 전 상담원id 존재여부 체크 API
---

#### 요청

| 항목 | 값                     | 설명 |
| ---- |-----------------------| --- |
| URL  | `GET` /kakaopay/clser |

| 이름       |   타입    | 필수 | 설명    |
|----------|:-------:| :---: |-------|
| clser_id | String  |  ○   | 상담원id |


#### 응답

`응답 내용`

| 이름 |   타입    | 필수 | 설명        |
| ---- |:-------:| :---: |-----------|
| body   | String  |  ○   | 중복ID 존재여부 |

`응답 예시`

```
중복된 ID가 없습니다. ID 사용 가능
```

### 상담원 정보 저장 API
---

#### 요청

| 항목 | 값                      | 설명 |
| ---- |------------------------| --- |
| URL  | `POST` /kakaopay/clser |

| 이름      |   타입    | 필수 | 설명   |
|---------|:-------:| :---: |------|
| clser_id | String  |  ○   | 상담원id |
| clser_nm | String  |  ○   | 상담원성명 |
| pwd     | String  |  ○   | 패스워드 |


#### 응답

`응답 내용`

| 이름 |   타입    | 필수 | 설명             |
| ---- |:-------:| :---: |----------------|
| body   | String  |  ○   | 상담원 정보 저장 성공여부 |

`응답 예시`

```
ID 생성 되었습니다.
```

### 상담원 로그인 API
---

#### 요청

| 항목 | 값                     | 설명 |
| ---- |-----------------------| --- |
| URL  | `GET` /kakaopay/clserLogin |

| 이름      |   타입    | 필수 | 설명   |
|---------|:-------:| :---: |------|
| clser_id | String  |  ○   | 상담원id |
| pwd     | String  |  ○   | 패스워드 |


#### 응답

`응답 내용`

| 이름 |   타입    | 필수 | 설명           |
| ---- |:-------:| :---: |--------------|
| body   | String  |  ○   | 상담원 로그인 성공여부 |

`응답 예시`

```
로그인 성공
```


### 미완료 문의내용 조회  API
---

#### 요청

| 항목 | 값                      | 설명 |
| ---- |------------------------| --- |
| URL  | `GET` /kakaopay/inquYn |


#### 응답

`응답 내용`

| 이름 |   타입    | 필수 | 설명           |
| ---- |:-------:| :---: |--------------|
| body   | String  |  ○   | 미완료 문의 내역 조회 |

`응답 예시`

```
[{"inqu_id":1,
"user_id":"8090666",
"inqu_titl":"결제문의",
"inqu_cn":"2번 결제됨",
"input_dthms":"2022-08-04 05:49:33"}
]
```

### 미완료 문의내용 중 상담원 지정  API
---

#### 요청

| 항목        | 값                      | 설명 |
|-----------|------------------------| --- |
| URL       | `PUT` /kakaopay/inquClser |

| 이름        |   타입    | 필수 | 설명   |
| --------- |:-------:| :---: |------|
| inqu_id   | String  |  ○   | 문의id |
| clser_id  | String  |  ○   | 상담원id |

#### 응답

`응답 내용`

| 이름 |   타입    | 필수 | 설명              |
| ---- |:-------:| :---: |-----------------|
| body   | String  |  ○   | 답변자 지정 저장 성공여부  |

`응답 예시`

```
선택하신 문의 내역의 답변자로 지정되었습니다.
```

### 상담원이 답변해야할 문의내역 조회  API
---

#### 요청

| 항목        | 값                         | 설명 |
|-----------|---------------------------| --- |
| URL       | `GET` /kakaopay/inquClser |

| 이름        |   타입    | 필수 | 설명   |
| --------- |:-------:| :---: |------|
| clser_id  | String  |  ○   | 상담원id |

#### 응답

`응답 내용`

| 이름 |   타입    | 필수 | 설명                |
| ---- |:-------:| :---: |-------------------|
| body   | String  |  ○   | 답변자가 답변해야 할 문의 조회 |

`응답 예시`

```
[{"inqu_id":1,
"user_id":"8090666",
"inqu_titl":"결제문의",
"inqu_cn":"2번 결제됨",
"input_dthms":"2022-08-04 05:49:33"}
]
```
### 답변 내용 저장  API
#### 요청

| 항목        | 값                    | 설명 |
|-----------|----------------------| --- |
| URL       | `POST` /kakaopay/rpl |

| 이름     |   타입    | 필수 | 설명   |
|--------|:-------:| :---: |------|
| inqu_id | String  |  ○   | 문의id |
| rpl_cn | String  |  ○   | 답변내용 |

#### 응답

`응답 내용`

| 이름 |   타입    | 필수 | 설명            |
| ---- |:-------:| :---: |---------------|
| body   | String  |  ○   | 답변 내용 저장 성공여부 |

`응답 예시`

```
답변이 저장 되었습니다.
```


* 문제해결전략
    * 상담원 pwd는 FE에서 암호화하여 보내주며 암호화한 값을 저장
    * 고객 문의 사항 등록 시 중복id있는지 확인 후 저장
    * 고객 문의 조회 시 같은 id로 2건 이상이 조회 시 문의내역id로 조회
    * 상담원은 기본 정보 저장 후 답변 등록 가능
    * 상담원은 문의 내역 중 자신이 답변할 문의를 결정한 후 답변 가능
* 총평
    * 아직 익숙하지 않은 FE(리액트)에 대한 부담떄문에 FE구성을 체계적으로 수행하지 못함
    * 회사에서 사용 중인 여러 편리한 도구들과 패키지들에 익숙해진 결과 과제 수행 시 생산성이 낮아짐
    * 리액트 관련 개발을 조금 더 자주 접하게 된다면 빠른 속도로 개발 가능할 것으로 보임
