package jpabook.jpashop.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 스프링과 자박스 두개가 있는데 스프링 쓰는게 더 낫다. 쓸수있느게 더 많다
//@AllArgsConstructor //생성자를 만들어주는것
@RequiredArgsConstructor // 생성자를 만드는건데 더 나은 방법
public class MemberService {
    
//	@Autowired //필드 인젝션  단점: 바꾸지를 못한다
	private final MemberRepository memberRepository;
	
//	@Autowired // setter 인젝션 장점: 테스트할때 많이사용 단점:런타임시점에 누군가가 바꿀수 있다
//	public void setMemberRepository(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
//	@Autowired  // 생성자 인젝션 요즘 제일 많이 사용하는 것 하나만 존재할때 어노테이션 없어도 해준다
//	public MemberService(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
	
	
	//회원 가입
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member); // 중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		//EXCEPTION
	List<Member> findMembers = memberRepository.findByName(member.getName()); // 같은이름 있는지 찾아보는거
	if(!findMembers.isEmpty()) {
		throw new IllegalStateException("이미 존재하는 회원입니다");
	}
		
	}
	
	//회원 전체 조회
//	@Transactional(readOnly = true) //읽기는 리드온니 트루 넣는게 좋다 쓰기에 넣으면 변경이안됨
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	// 단건 조회
//	@Transactional(readOnly = true)
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
}
