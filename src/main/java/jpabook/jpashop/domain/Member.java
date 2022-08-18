package jpabook.jpashop.domain;

import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "name")
    private String username;

/*  단방향으로 먼저 설계
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order){
        order.setMember(this);
        orders.add(order);
    }*/

    private String city;

    private String street;

    private String zipcode;

}
