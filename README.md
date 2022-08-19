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
* 설계시 단방향 매핑만(Many 쪽에서 설정)으로 설계를 완료해야 한다.
* 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐이다.
* Member가 List<Order>를 가지고 있는 것은 설계 상 좋다고 할 수 없다. ➡ 보통 회원의 주문량을 조회할 때 ORDERS 테이블에서 출발하기 때문

---

#### 🔅 다양한 연관관계 매핑
* 일대다 단방향 매핑보다는 **다대일 양방향 매핑**을 사용하자
* RDB의 유니크 제약 조건이 추가된 것이 일대일 매핑이다. ➡ 다대일 단방향 매핑과 유사
* 다대일을 사용하면 그것을 연관관계 주인으로 써야 한다.(스펙상 mappedby 가 없음)
* 다대다 매핑의 한계
  - 서비스를 운영하다 보면 주문시간, 수량같은 데이터가 들어올 수 있는데,이것을 연결 테이블에 넣을 수 없다.
    - 연결 테이블용 엔티티를 추가하자 : @ManyToMany ➡ @OneToMany, @ManyToOne
    - 연결 테이블은 매핑하고자 하는 테이블들의 PK 값을 FK로 갖는데, 이때 이 두 가지 값을 엮어서 PK, FK로 설정하는 것보다 비즈니스적 의미가 없는 값으로 PK를 따로 잡는 것을 권장한다.(장기적으로 볼 때 보다 유연하게 애플리케이션 개발 가능)

***

#### 🔅 상속관계 매핑
* 상속관계 매핑: 객체의 상속과 구조와 DB의 슈퍼타입 서브타입 관계를 매핑
* 조인전략 
  - @DiscriminatorColumn : 어떤 서브 테이블에서 추가 되었는지 타입을 나타내는 Column을 만들어줌(DTYPE) ➡ 단일 테이블 전략에서는 반드시 필요(해당 어노테이션을 안넣어줘도 생김)
  - @Inheritance(strategy=IngeritanceType.XXX) 
    - JOINED: 조인 전략
    - SINGLE_TABLE: 단일 테이블 전략 - 성능에 유리
    - TABLE_PER_CLASS: 구현 클래스마다 테이블 전략
  - @DiscriminatorValue("XXX")
  - 👍 설계를 수정할 때 소스코드의 여러 곳을 변경할 필요없이 전략부분만 바꿔주면 된다.(JPA의 장점)
  - 비즈니스 적으로 복잡하거나 확장성을 고려해야 한다면 조인 전략, 단순하고 확장성이 적은 경우에는 단일 테이블 전략을 추천한다.(TABLE_PER_CLASSS 이건 쓰지말자.)
* @MappedSuperclass
  - 공통 매핑 정보가 필요할 때 사용(ex. id, name)
  - Entity아님, 상속관계 매핑X
  - 조회, 검색 안됨
  - 직접 생성해서 사용할 일이 없으므로 **추상클래스 권장**
  - 데이터가 많아지면(like 하루에 백만건) 상속관계가 잘 동작하지 않을 수 있다. 관계를 단순하게 가져가는 게 이득일 수 있다.