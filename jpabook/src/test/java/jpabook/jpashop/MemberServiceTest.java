package jpabook.jpashop;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;

@RunWith(SpringRunner.class)  // ������ ���� �����ҷ�
@SpringBootTest //������ ��Ʈ ��� ���·� �Ϸ��� �־����
@Transactional // Ʈ����� �ɰ� �׽�Ʈ�Ѵ��� �� �ѹ��ع���
public class MemberServiceTest {
  
	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	@Autowired EntityManager em; //insert�� ������
	
	@Test
//	@Rollback(false) //insert �� persist������ �ȵǼ� �ѹ� false ����
	                 // �׽�Ʈ�� �ٷ� �����Ѵ��� �ѹ� �Ǳ⋚����
	public void ȸ������()throws Exception{
		//given
		Member member = new Member();
		member.setName("kim");
		
		//when
		Long saveId = memberService.join(member);
		
		//then
		em.flush(); //insert�� ������
		assertEquals(member, memberRepository.findOne(saveId));
	}
	
	@Test(expected = IllegalStateException.class)
	public void �ߺ�_ȸ��_����()throws Exception{
		//given
		Member member1 = new Member();
		member1.setName("kim1");
		
		Member member2 = new Member();
		member2.setName("kim1");
		
		//when
		memberService.join(member1);
		// @Test(expected = IllegalStateException.class) �̰� �ϸ� try catch ���ص���
//		try {          
//			memberService.join(member2);
//		} catch (IllegalStateException e) {
//			return;
//		}
		memberService.join(member2); //���ܰ� �߻��ؾ��Ѵ�
		
		//then
		fail("���ܰ� �߻��ؾ� �Ѵ�.");
	}
	
}
