package jpabook.jpashop;

import com.sun.org.apache.xpath.internal.operations.Or;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        // 트랜잭션 얻어오기
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
/*            Order order = new Order();
            order.addOrderItem(new OrderItem());

            Order order1 = new Order();
            em.persist(order1);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order1);
            em.persist(orderItem);

            // 상속관계 매핑
            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");

            em.persist(book);
*/

            // 프록시
            Member member = new Member();
            member.setName("김영한");
            em.persist(member);

            em.flush();
            em.clear();

            Member member1 = new Member();
            member1.setName("member1");
            em.persist(member1);

            Member member2 = new Member();
            member1.setName("member2");
            em.persist(member2);

            Member m1 = em.getReference(Member.class, member1.getId());
            Member m2 = em.getReference(Member.class, member2.getId());

            logic(m1, m2);

            /*
            /**
             * (1) em.find() : 데이터베이스를 통해 실제 엔티티 객체 조회
             * /
            Member findMember = em.find(Member.class, member.getId());
            System.out.println("findMember.id = "+findMember.getId());
            System.out.println("findMember.name = "+findMember.getName());
*/
            /**
             * (2) em.getReference() : 데이터베이스 조회를 미루는 가짜(프록시)
             */
            Member findMember = em.getReference(Member.class, member.getId());
            System.out.println("findMember.id = "+findMember.getId()); // getReference()를 Id값으로 찾았기 때문에 DB에서 정보를 가져오지 않음
            // DB에 쿼리 날아가는 시점
            System.out.println("findMember.name = "+findMember.getName());

            tx.commit(); // DB에 반영하자. 이거 안 쓰면 Connection leak detected 에러남.
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void logic(Member m1, Member m2) {
        // 프록시 객체는 원본 엔티티를 상속받기 때문에 타입 체크시 주의
        System.out.println("m1 : "+ (m1 instanceof Member));
        System.out.println("m1 : "+ (m2 instanceof Member));
    }
}
