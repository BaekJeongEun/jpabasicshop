package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ElementCollection // 값 타입을 하나 이상 저장할 때 사용
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID") // 외래키로 잡음
    ) // 컬렉션 저장하기 위한 별도의 테이블이 필요함

    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods  = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns =
        @JoinColumn(name = "MEMBER_ID") // 외래키로 잡음
    )
    private List<Address> addressHistory = new ArrayList<>();

/*    // 주소
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
*/

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<Address> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<Address> addressHistory) {
        this.addressHistory = addressHistory;
    }

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
