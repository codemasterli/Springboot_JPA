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
@Transactional(readOnly = true) // �������� �ڹڽ� �ΰ��� �ִµ� ������ ���°� �� ����. �����ִ��� �� ����
//@AllArgsConstructor //�����ڸ� ������ִ°�
@RequiredArgsConstructor // �����ڸ� ����°ǵ� �� ���� ���
public class MemberService {
    
//	@Autowired //�ʵ� ������  ����: �ٲ����� ���Ѵ�
	private final MemberRepository memberRepository;
	
//	@Autowired // setter ������ ����: �׽�Ʈ�Ҷ� ���̻�� ����:��Ÿ�ӽ����� �������� �ٲܼ� �ִ�
//	public void setMemberRepository(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
//	@Autowired  // ������ ������ ���� ���� ���� ����ϴ� �� �ϳ��� �����Ҷ� ������̼� ��� ���ش�
//	public MemberService(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
	
	
	//ȸ�� ����
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member); // �ߺ� ȸ�� ����
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		//EXCEPTION
	List<Member> findMembers = memberRepository.findByName(member.getName()); // �����̸� �ִ��� ã�ƺ��°�
	if(!findMembers.isEmpty()) {
		throw new IllegalStateException("�̹� �����ϴ� ȸ���Դϴ�");
	}
		
	}
	
	//ȸ�� ��ü ��ȸ
//	@Transactional(readOnly = true) //�б�� ����´� Ʈ�� �ִ°� ���� ���⿡ ������ �����̾ȵ�
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	// �ܰ� ��ȸ
//	@Transactional(readOnly = true)
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
}
