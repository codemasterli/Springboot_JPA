package jpabook.jpashop.domain;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
  // jpql select 0 from order o; => sql select * from order
    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @JsonIgnore
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //연관관계 메서드//
    public void  setMember(Member member) {
    	this.member = member;
    	member.getOrders().add(this);
    }
    
    public static void main(String[] args) {
    	Member member = new Member();
    	Order order = new Order();
    	
    	order.setMember(member);
    }

     public void addOrderItem(OrderItem orderItem) {
    	 orderItems.add(orderItem);
    	 orderItem.setOrder(this);
     }
     //양방향일때 세팅
     public void setDelivery(Delivery delivery) {
    	 this.delivery = delivery;
    	 delivery.setOrder(this);
     }
     
     //==생성 매서드
     public static Order createOrder(Member member, Delivery delivery,OrderItem...orderItems) {
    	 Order order = new Order();
    	 order.setMember(member);
    	 order.setDelivery(delivery);
    	 for (OrderItem orderItem : orderItems) {
    		 order.addOrderItem(orderItem);
    		
    	 }
    	 order.setStatus(OrderStatus.OREDER);
    	 order.setOrderDate(LocalDateTime.now());
    	 return order;
     }
     
     //비즈니스 로직
     // 주문취소
     public void cancel() {
    	 if (delivery.getStatus() == DeliveryStatus.COMP) {
    		 throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
    		 
    	 }
    	 this.setStatus(OrderStatus.CANCEL);
    	 for (OrderItem orderItem : orderItems) {
    		 orderItem.cancel();
    	 }
     }
 
     //조회 로직
     //전체 주문 가격조회
     public int getTotalPrice() {
    	 int totalPrice = 0;
    	 for(OrderItem orderItem : orderItems) {
    		 totalPrice += orderItem.getTotalPrice();
    	 }
    	 return totalPrice;
    	 
    	 // 위랑 똑같은 로직이지만 더 간단하게 사용할수 있다
//    	return orderItems.stream()	
//    			.mapToInt(OrderItem::getTotalPrice)
//    			.sum();
     }


}
