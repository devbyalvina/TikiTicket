# TikiTicket

## 프로젝트 소개

이 프로젝트는 대규모 트래픽에 대응할 수 있는 콘서트 티켓 예매 서비스로, 많은 사용자가 동시에 접속해도 안정적으로 서비스를 제공할 수 있도록 설계되었습니다. 
대규모 트래픽 상황에도 콘서트 티켓을 예매하는 서비스에 대해 안정적이고 원활한 사용자 경험을 제공합니다.  

<br/>

**주요기능**

- 콘서트 목록 조회하기
- 콘서트 좌석 선택하기
- 콘서트 티켓 예매하기
- 포인트 충전하기
- 포인트로 콘서트 티켓 결제하기

**기술적 고찰**

[동시성 문제 해결하기](https://github.com/devbyalvina/TikiTicket/blob/main/docs/%EB%8F%99%EC%8B%9C%EC%84%B1%EB%AC%B8%EC%A0%9C.md)  
[브랜치 전략](https://github.com/devbyalvina/TikiTicket/blob/main/docs/%EB%B8%8C%EB%9E%9C%EC%B9%98%EC%A0%84%EB%9E%B5.md)

---

## TikiTicket 시작하기

**Requirements**

- SpringBoot 3.2.4
- JDK 21

**실행방법**

1. 저장소를 클론합니다.

```bash
git clone https://github.com/devbyalvina/TikiTicket.git
```

2. 프로젝트 디렉토리로 이동합니다.

```bash
 cd TikiTicket
```

3. 시스템에 JDK 21이 설치되어 있는지 확인합니다.
4. Spring Data에서 지원하는 데이터베이스를 구성합니다.
5. `application.properties`를 데이터베이스 연결 정보와 기타 필요한 설정으로 구성합니다.
6. 프로젝트를 빌드합니다.

```bash
./gradlew build
```

7. 애플리케이션을 실행합니다.
