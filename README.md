####🔅 Order에 MemberId를 쓰는 것은 객체지향스럽지 않다.
```
* orderId를 통해서 Member 조회

Order order = em.find(Order.class, 1L);
Long memberId = order.getMemberId();

Member member = em.find(Member.class, memberId);

➡ 번거로운 조회과정을 거쳐야 한다.
```

* memberId 보다는 Member 자체를 사용

```
@Column(name = "MEMBER_ID")
private Long memberId;
```
➡ 이런 식의 설계는 관계형 DB에 맞추는 설계이다.

***

#### 🔅 연관관계 매핑 - 양방향 매핑

* 테이블에서 FK를 갖는 쪽을 연관관계 주인으로 하자.
  - 예) ORDER가 MEMBER_ID라는 FK를 갖는다. Order Entity가 연관관계 주인
  - Member의 List<Order> 는 연관관계 지음을 당한 것.(가짜매핑, mappedBy) >> 읽기 전용으로 조회만 가능하다(update, create ❌)
* 연관 관계 주인에 값을 입력해야 하지만 _**순수한 객체 관계를 고려하면 항상 양쪽 모두에 값을 입력해야 한다.**_
  - 연관 관계 편의 메서드는 한쪽에서만 사용한다.
* 무한 루프를 주의하자.(예. lombok으로 자동 생성된 toString())
  - 컨트롤러에서 Entity를 반환할 때 JSON 생성 라이브러리에 의해 무한 루프에 빠질 수 있다.
    - ❗ 컨트롤러에서 Entity를 반환하지 말자. ➡ Entity에 변경이 있을 수 있기 때문에 API spec에 문제가 생길 수 있다.(DTO를 사용하자.)

##### 양방향 매핑 정리
* 설계시 단방향 매핑만으로 설계를 완료해야 한다.
* 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐이다.