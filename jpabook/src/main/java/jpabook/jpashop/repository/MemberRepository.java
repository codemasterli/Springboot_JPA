package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository  //������ ������ ������ش� . component(������̼�)
@RequiredArgsConstructor
public class MemberRepository {
  
//	@PersistenceContext
	@Autowired
	private final EntityManager em;
	
//	public MemberRepository(EntityManager em) {
//		this.em = em;
//	}
	
	// persist Ŀ�ԵǴ½��� �ݿ�
	public void save(Member member) {
		em.persist(member);;
	}
	// jpa �� find�޼ҵ� ��� �ܰ� ���
	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}
	
	public List<Member> findAll(){// ��ü�� ������� ������ ���� sql�� ��������� �� �ٸ�
		return 	em.createQuery("select m from Member m",Member.class)  
		  .getResultList();	
	}
	
	public List<Member> findByName(String name){
		return em.createQuery("select m from Member m where m.name = :name" , Member.class)
				.setParameter("name", name)
				.getResultList();
	}
	
}
