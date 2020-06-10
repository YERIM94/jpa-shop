package jpabook.jpashop.domain.item;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Movie extends Item{
	private String director;
	private String actor;
}
