package jpabook.jpashop;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author yerim
 * 
 * 총 2주문
 * * userA
 * 	* JPA1 BOOK
 * 	* JPA2 BOOK
 * * userB
 * 	* SPRING1 BOOK
 * 	* SPRING2 BOOK
 *
 */
@Component
@RequiredArgsConstructor
public class InitDb {
	
	private final InistService initService;
	
	@PostConstruct
	public void init() {
		initService.dbInit1();
	}
	
	
	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService{
		
		private final EntityManager em;
		public void dbInit1() {
			Member member = new Member();
			member.setName("userA");
			member.setAddress(new Address("서울","1","1111"));
			em.persist(member);
			
			Book book1 = new Book();
			book1.setName("JPA1 BOOK");
			book1.setPrice(10000);
			book1.setStockQuantity(100);
			em.persist(book1);
			
			Book book2 = new Book();
			book2.setName("JPA2 BOOK");
			book2.setPrice(20000);
			book2.setStockQuantity(100);
			em.persist(book2);
			
		}
	}
	
}

