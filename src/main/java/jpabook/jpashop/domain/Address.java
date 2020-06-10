package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Address {
	private String city;
	private String address;
	private String zipcode;
	
	protected Address() {
	}
}
