package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Member {
   @Id  @GeneratedValue
   @Column(name = "member_id")
	private Long id;
   
   private String name;
   
   @Embedded
   private Address address;
   
   @JsonIgnore
   @OneToMany(mappedBy = "member") // order테이블에 있는 member에의해 맵핑된거 뿐
   private List<Order> orders = new ArrayList<>();
}
