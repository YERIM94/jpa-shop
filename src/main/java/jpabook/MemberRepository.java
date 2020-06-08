package jpabook;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
	
	@PersistenceContext
	private EntityManager em; //@PersistenceContext 어노테이션 사용하면 springboot가 entityManager 알아서 주입해줌  
	
	public Long save(Member member) {
		em.persist(member);
		return member.getId();
	}

	public Member find(Long id) {
		return em.find(Member.class, id);
	}
}
