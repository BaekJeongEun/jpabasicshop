package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

@Embeddable // 값 타입을 정의하는 곳에 표시
public class Address {

    // 주소
    private String city;
    private String street;
    private String zipcode;

    public Address() { // 기본 생성자 필수
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
