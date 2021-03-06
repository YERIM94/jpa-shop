package jpabook.jpashop.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) //readOnly = true -> 읽기전용에만 
@RequiredArgsConstructor //final 붙은 필드만 생성자 만들어줌(생성자injection)
public class MemberService {

	private final MemberRepository memberRepository;
	
	/** 회원가입 
	 * @param member
	 * @return
	 */
	@Transactional //따로 설정한게 우선 
	public Long join(Member member) {
		validateDuplicateMember(member); //중복회원검증 
		memberRepository.save(member);
		
		return member.getId();
	}
	
	private void validateDuplicateMember(Member member) {
		//EXCEPTION
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	//회원전체조회
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
	
	@Transactional
	public void update(Long id, String name) {
		Member member = memberRepository.findOne(id);
		member.setName(name);
		//update된 member 반환해도 되지만, 그러면 하나의 메소드 안에 조회, 변경성 쿼리 둘 다 들어가서 비추(걍 개인 개발스타일) 
	}
}
