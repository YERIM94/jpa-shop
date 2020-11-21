package jpabook.jpashop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * xTo One
 * Order
 * Order -> Member
 * Order -> Delivery
 * @author yerim
 *
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
	
	private final OrderRepository orderRepository;
	
	@GetMapping("/api/v1/simple-orders")
	public List<Order> ordersV1(){
		List<Order> all = orderRepository.findAll(new OrderSearch());
		for(Order order : all) {
			order.getMember().getName();
			order.getDelivery().getAddress();
		}
		return all;
	}
	
	@GetMapping("/api/v2/simple-orders")
	public List<SimpleOrderDto> ordersV2(){
		List<Order> orders = orderRepository.findAllByString(new OrderSearch());
		
		List<SimpleOrderDto> result =  orders.stream()
				.map(o->new SimpleOrderDto(o))
				.collect(Collectors.toList());
		
		return result;
	}

	@Data
	static class SimpleOrderDto{
		private Long orderId;
		private String name;
		private LocalDateTime orderDate;
		private OrderStatus orderStatus;
		private Address address;
		
		public SimpleOrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName(); //LAZY 초기화 
			orderDate = order.getOrderDate();
			orderStatus = order.getStatus();
			address = order.getDelivery().getAddress(); //LAZY 초기화 
			
		}
	}
}
