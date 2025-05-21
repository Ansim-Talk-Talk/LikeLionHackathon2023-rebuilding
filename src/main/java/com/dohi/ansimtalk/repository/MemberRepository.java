package com.dohi.ansimtalk.repository;

import com.dohi.ansimtalk.domain.Member;
import com.dohi.ansimtalk.exception.NotEnoughStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        // em.persist(member);
        if (member.getId() == null) {
            em.persist(member);
        } else {
            em.merge(member);
        }
    }

    public void merge(Member member) {
        em.merge(member);

    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    //폰넘버로 사람검색
    public List<Member> findByPhone(String phone) {
        return em.createQuery("select m from Member m where m.phone = :phone", Member.class)
                .setParameter("phone", phone)
                .getResultList();
    }



        public List<Member> findByCodes(String code) {
            return em.createQuery("select m from Member m where m.code = :code", Member.class)
                    .setParameter("code", code)
                    .getResultList();
        }


    public Member findByCode(String code) {
        List<Member> members = em.createQuery("select m from Member m where m.code = :code", Member.class)
                .setParameter("code", code)
                .getResultList();
        if (members.isEmpty()) {
            // 해당 코드에 맞는 회원이 없는 경우 처리
            return null; // 또는 예외 처리에 맞게 다른 값을 반환
        }

        return members.get(0); // 첫 번째 회원 반환
    }
}