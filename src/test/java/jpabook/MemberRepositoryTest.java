package jpabook;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import jpabook.jpashop.MemberRepository;
import jpabook.jpashop.domain.Member;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest 
@Rollback(false)
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Test
	@Transactional
	public void testMember() throws Exception{
		//given
		Member member = new Member();
		member.setName("memberA");
		
		//when
		Long savedId = memberRepository.save(member);
		Member findMember = memberRepository.find(savedId);
		
		//then
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
		Assertions.assertThat(findMember).isEqualTo(member);  
	}

}
