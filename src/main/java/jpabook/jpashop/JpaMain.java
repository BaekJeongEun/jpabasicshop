package jpabook.jpashop;

import com.sun.org.apache.xpath.internal.operations.Or;
import jpabook.jpashop.domain.*;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
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

            /**
             * (1) em.find() : 데이터베이스를 통해 실제 엔티티 객체 조회
             */
            //Member findMember = em.find(Member.class, member.getId());
            //System.out.println("findMember.id = "+findMember.getId());
            //System.out.println("findMember.name = "+findMember.getName());

            /**
             * (2) em.getReference() : 데이터베이스 조회를 미루는 가짜(프록시)
             */
            Member findMember = em.getReference(Member.class, member.getId());
            System.out.println("findMember.id = "+findMember.getId()); // getReference()를 Id값으로 찾았기 때문에 DB에서 정보를 가져오지 않음
            // DB에 쿼리 날아가는 시점
            System.out.println("findMember.name = "+findMember.getName());

            /**
             * 프록시 확인
             * 프록시 인스턴스의 초기화 여부 확인
             */
            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());; // Proxy
            refMember.getName(); // 강제 초기화

            Hibernate.initialize(refMember); // 강제 초기화

            /**
             * 영속성 전이(CASCADE)와 고아 객체
             */
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
            // Parent의 childList에서 @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL) 속성으로 지정하여 자동 저장
            // em.persist(child1);
            // em.persist(child2);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);
            
            /**
             * 임베디드 타입
             */
            Member member3 = new Member();
            member3.setName("hello Embedded Type");
            Address address = new Address("city", "street", "1000");
            member3.setHomeAddress(address);
            member3.setWorkPeroid(new Peroid());
            em.persist(member3);

            /**
             * 값 타입과 불변 객체
             */
            Address newAddress = new Address("New City", address.getStreet(), address.getZipcode());
            member3.setHomeAddress(newAddress); // Address의 속성을 바꾸려면 객체 자체를 통째로 갈아끼워야 함.

            /**
             * 값 타입 컬렉션 사용
             */
            Member member4 = new Member();
            member4.setName("member4");
            member4.setHomeAddress(new Address("homeCity", "street", "1000"));
        
            member4.getFavoriteFoods().add("치킨");    
            member4.getFavoriteFoods().add("족발");    
            member4.getFavoriteFoods().add("피자");

            member4.getAddressHistory().add(new Address("old1", "street", "1000"));
            member4.getAddressHistory().add(new Address("old2", "street", "1000"));

            em.persist(member4);

            em.flush();
            em.clear();

            System.out.println("========== START ==========");
            Member findMember1 = em.find(Member.class, member4.getId());
            // homeCity -> newCity
            // findMember1.getFavoriteFoods().remove("치킨"); (X)
            Address a = findMember1.getHomeAddress();
            findMember1.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            // 치킨 -> 한식
            findMember1.getFavoriteFoods().remove("치킨");
            findMember1.getFavoriteFoods().add("한식");
            findMember1.getAddressHistory().remove(new Address("old1", "street", "1000"));
            findMember1.getAddressHistory().add(new Address("newCity1", "street", "1000"));

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
