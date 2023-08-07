# 원티드 백엔드 프리온보딩 챌린지 사전미션
> 개발 내역에 대한 요약  
> + 기본 개발과제 1~7  
> + 단위/통합 테스트 코드 작성  
> + AWS EC2 배포

<br>

### ✔️️️ 지원자의 성명
##### 양일표

<br>

### ✔️️️ 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
##### AWS EC2 인스턴스를 이용한 접근
+ ```http://13.209.104.109:8080/{api}```
  + ex) ```http://13.209.104.109:8080/api/user/register``` 와 같이 접근 가능합니다.
+ 저는 테스트를 위해 **postman**을 사용했습니다. (시연영상 참조)

<br>

### ✔️️ 데이터베이스 테이블 구조
+ 데이터베이스는 크게 **USER**, **BOARD** 테이블로 구성되어 있으며 세부 항목은 다음과 같습니다.

![erd.png](readme_img/erd.png)

<br>

### ✔️ 구현한 API의 동작을 촬영한 데모 영상 링크
+ [Postman 이용한 AWS 사용 시연영상](https://youtu.be/NPi2DFE2PtI)

<br>

### ✔️️ 구현 방법 및 이유에 대한 간략한 설명
+ 가장 익숙하고 자신있는 언어인 Java와 Spring을 사용했습니다.
+ 배포는 AWS EC2로 Git Clone을 이용한 방식을 사용해 배포했습니다.
+ war 파일로 Filezila 배포 방식보다 간단한 api이기 때문에 내장 톰캣을 이용해 jar 배포 처리했습니다.
+ 추가 고려사항
  + 직렬화 이슈로 board의 @ManyToOne에 해당하는 변수인 User 객체를 @JsonIgnore 처리했지만, 실제 사용시 user 정보가 필요하다면 querydsl로 해당하는 dto 정보로 가져올 수 있는 방안을 고려해야 할 것 같습니다.

<br>

### ✔️️ API 명세(request/response 포함) [URL](https://ilpyo.notion.site/API-d989a5067e384350ae50c3022a503eec?pvs=4)
