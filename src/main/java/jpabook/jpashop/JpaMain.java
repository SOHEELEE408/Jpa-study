package jpabook.jpashop;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

           /* // 저장
            Member member = new Member();
            member.setUsername("userA");
            em.persist(member);

            Order order = new Order();
            order.setMember(member);
            em.persist(order);

            em.flush();
            em.clear();

            // 조회

            Order findOrder = em.find(Order.class, order.getId());
            Member findMember = findOrder.getMember();

            System.out.println("findMember => "+ findMember.getUsername());

            // order의 memberid 바꾸기

            Member newMember = new Member();
            newMember.setUsername("new user");

            em.persist(newMember);

            findOrder.setMember(newMember);

            System.out.println("find order's member => "+ findOrder.getMember().getUsername());

            // orders 조회

            Member memberA = em.find(Member.class, 3L);

            List<Order> orderA = memberA.getOrders();

            System.out.println("orderA.size() => "+ orderA.size());



         for(Order or : orders){
                System.out.println("order => "+or.getId());
            }
            */

            Order order = new Order();
            order.addOrderItem(new OrderItem());

            tx.commit();
        } catch(Exception exception){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
