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

@RunWith(SpringRunner.class)  // 스프링 같이 실행할래
@SpringBootTest //스프링 부트 띄운 상태로 하려면 있어야함
@Transactional // 트랜잭션 걸고 테스트한다음 다 롤백해버림
public class MemberServiceTest {
  
	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	@Autowired EntityManager em; //insert문 보여줌
	
	@Test
//	@Rollback(false) //insert 가 persist에서는 안되서 롤백 false 설정
	                 // 테스트는 바로 실행한다음 롤백 되기떄문에
	public void 회원가입()throws Exception{
		//given
		Member member = new Member();
		member.setName("kim");
		
		//when
		Long saveId = memberService.join(member);
		
		//then
		em.flush(); //insert문 보여줌
		assertEquals(member, memberRepository.findOne(saveId));
	}
	
	@Test(expected = IllegalStateException.class)
	public void 중복_회원_예외()throws Exception{
		//given
		Member member1 = new Member();
		member1.setName("kim1");
		
		Member member2 = new Member();
		member2.setName("kim1");
		
		//when
		memberService.join(member1);
		// @Test(expected = IllegalStateException.class) 이거 하면 try catch 안해도됨
//		try {          
//			memberService.join(member2);
//		} catch (IllegalStateException e) {
//			return;
//		}
		memberService.join(member2); //예외가 발생해야한다
		
		//then
		fail("예외가 발생해야 한다.");
	}
	
}
