package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository //component스캔의 대상이 돼서 자동으로 스프링 빈에 등록뒴 
@RequiredArgsConstructor //EntityManager는 원래 @Autowired 아니라 @PersistenceContext 표준어노테이션 사용해야 하지만 스프링에서 @Autowired도 허용해줌 -> @RequiredArgsConstructor도 사용가능  
public class MemberRepository {
	
	private final EntityManager em;
	
	public void save(Member member) {
		em.persist(member);
	}
	
	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}
	
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}
	
	public List<Member> findByName(String name){
		return em.createQuery("select m from Member m where m.name = :name", Member.class)
				.setParameter("name",  name)
				.getResultList();
	}
}
