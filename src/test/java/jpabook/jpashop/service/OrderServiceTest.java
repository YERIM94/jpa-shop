package jpabook.jpashop.service;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Test
	public void 상품주문() throws Exception{
		//given
		Member member = createMember();
		Item book = createBook("hihi",10000,10);
		
		int orderCount = 2;
		
		//when
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		
		//then
		Order getOrder = orderRepository.findOne(orderId);
		
		Assert.assertEquals("상품주문 시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());	
		Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
		Assert.assertEquals("총 가격은 주문수량 * 가격이다.", 10000*orderCount, getOrder.getTotalPrice());
		Assert.assertEquals("주문하면 주문수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());
	}
	
	@Test
	public void 주문취소() throws Exception{
		//given
		
		//when
		
		//then
	}
	
	@Test(expected = NotEnoughStockException.class)
	public void 상품주문_재고수량초과() throws Exception{
		//given
		Member member = createMember();
		Item book = createBook("hihi",10000,10);
		
		int orderCount = 19;
		//when
		orderService.order(member.getId(), book.getId(), orderCount); 
		
		//then
		fail("재고수량 부족 예외가 발생해야 함");
	}
	
	private Member createMember() {
		Member member  = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울","강가","123-123"));
		em.persist(member);
		
		return member;
	}
	
	private Book createBook(String name, int price, int stockQuantity) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		em.persist(book);
		
		return book;
	}
}
