package jpabook.jpashop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;

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
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    
	@Autowired
	EntityManager em;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orderRepository;
	
	@Test
	public void ��ǰ�ֹ�() throws Exception {
		//given
		Member member = createMember();
		
	
		Book book = createBook("�ð� Jpa",10000,10);
		
		int orderCount = 2;
		//when
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		//then
		Order getOrder= orderRepository.findOne(orderId);
		
		Assert.assertEquals("��ǰ �ֹ��� ���´� ORDER", OrderStatus.OREDER,getOrder.getStatus());
		Assert.assertEquals("�ֹ��� ��ǰ�������� ��Ȯ�ؾ��Ѵ�",1,getOrder.getOrderItems().size());
		Assert.assertEquals("�ֹ� ������ * ����",10000 * orderCount,getOrder.getTotalPrice());
		Assert.assertEquals("�ֹ� ������ŭ ��� �پ���Ѵ�",8,book.getStockQuantity());
	}
	private Member createMember() {
		Member member = new Member();
		member.setName("ȸ��1");
		member.setAddress(new Address("����","���","123-123"));
		em.persist(member);
		return member;
	}
	
	private Book createBook(String name,int price, int stockQuantity) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		em.persist(book);
		return book;
	}
	
	@Test
	public void �ֹ����() throws Exception {
		//given
        Member member = createMember();
        Book item = createBook("�ð� JPA", 10000, 10);
		
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);//�ֹ��Ѱű��� ��
		//when
        orderService.cancelOrder(orderId);
		
		//then
	    Order getOrder = orderRepository.findOne(orderId);
	    
	    assertEquals("�ֹ� ��ҽ� ���� cancel", OrderStatus.CANCEL,getOrder.getStatus());
	    assertEquals("�ֹ� ��ҵ� ��ǰ�� �׸�ŭ ��� ����", 10, item.getStockQuantity());
	    
	}
	
	@Test(expected = NotEnoughStockException.class)
	public void ��ǰ�ֹ�_�������ʰ�() throws Exception {
		//given
		Member member = createMember();
		Item item = createBook("�ð� Jpa", 10000,10);
		
		int orderCount = 11;
		
		//when
		orderService.order(member.getId(), item.getId(), orderCount);
		
		//then
		fail("��� ���� ���� ���ܰ� �߻��ؾ� �Ѵ�.");
	}
}
