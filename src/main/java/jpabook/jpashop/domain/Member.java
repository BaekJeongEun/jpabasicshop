package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String name;

    // 기간 Peroid
    @Embedded // 값 타입을 사용하는 곳에 표시
    private Peroid workPeroid;

    // 주소
    @Embedded
    private Address homeAddress;

    // 주소
    @Embedded
    @AttributeOverrides({ // 한 엔티티에서 같은 값 타입을 사용하기 위함
            @AttributeOverride(name="city",
                    column = @Column(name="WORK_CITY")), // DB 칼럼명 중복 방지를 위한 지정
            @AttributeOverride(name="street",
                    column = @Column(name="WORK_STREET")),
            @AttributeOverride(name="zipcode",
                    column = @Column(name="WORK_ZIPCODE"))
    })
    private Address workAddress;

    public Peroid getWorkPeroid() {
        return workPeroid;
    }

    public void setWorkPeroid(Peroid workPeroid) {
        this.workPeroid = workPeroid;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
