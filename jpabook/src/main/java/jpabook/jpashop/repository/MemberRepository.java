package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository  //스프링 빈으로 등록해준다 . component(어노테이션)
@RequiredArgsConstructor
public class MemberRepository {
  
//	@PersistenceContext
	@Autowired
	private final EntityManager em;
	
//	public MemberRepository(EntityManager em) {
//		this.em = em;
//	}
	
	// persist 커밋되는시전 반영
	public void save(Member member) {
		em.persist(member);;
	}
	// jpa 에 find메소드 사용 단건 사용
	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}
	
	public List<Member> findAll(){// 객체를 대상으로 쿼리라 기존 sql과 비슷하지만 좀 다름
		return 	em.createQuery("select m from Member m",Member.class)  
		  .getResultList();	
	}
	
	public List<Member> findByName(String name){
		return em.createQuery("select m from Member m where m.name = :name" , Member.class)
				.setParameter("name", name)
				.getResultList();
	}
	
}
