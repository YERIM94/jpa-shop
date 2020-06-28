package jpabook.jpashop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepository  memberRepository;
	
	@Autowired
	private EntityManager em;
	
	@Test
	/* @Rollback(false) //spring에서 test code에 transactional이 있으면 실행 후 롤백시킴. 이를 방지하기 위한 것. */
	public void 회원가입() throws Exception{
		//given(주어졌을때)
		Member member = new Member();
		member.setName("kim");
		
		//when(실행하면)
		Long savedId = memberService.join(member);
		
		//then(이렇게됨)
		em.flush(); //영속성 context 안에 있는 내용을 DB에 반영(Transactional 어노테이션 있으므로 반영 후 다시 롤백됨) 
		assertEquals(member, memberRepository.findOne(savedId));
	}
	
	@Test(expected = IllegalStateException.class)
	public void 중복_회원_예외() {
		//given
		Member member1 = new Member();
		member1.setName("kim");
		
		Member member2 = new Member();
		member2.setName("kim");
		
		//when
		memberService.join(member1);
		memberService.join(member2);
		
		//then
		fail("예외가 발생해야 한다.");
	}

}
