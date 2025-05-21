package com.dohi.ansimtalk.repository;

import com.dohi.ansimtalk.domain.Member;
import com.dohi.ansimtalk.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {

    private final EntityManager em;

    public void save(Message message) {
        // em.persist(member);
        if (message.getId() == null) {
            em.persist(message);
        } else {
            em.merge(message);
        }
    }


    public Message findOne(Long id) {
        return em.find(Message.class, id);
    }
/*
    public Member findOnepartner(String partner) {
        return em.find(Member.class, partner);
    }
*/
    public List<Message> findAll() {
        return em.createQuery("select m from Message m", Message.class)
                .getResultList();
    }

    public List<Message> findByReceiveid(Long receiveid) {
        Long id = findOneReid(receiveid);
        List<Message> resultList = em.createQuery("select m from Message m where m.receiveid = :receiveid AND m.id=:id ORDER BY m.id DESC ", Message.class)
                .setParameter("receiveid", receiveid)
                .setParameter("id", id)
                .getResultList();
        return resultList;
    }

    public List<Message> findByReceiveid2(Long receiveid) {
        List<Message> resultList = em.createQuery("select m from Message m where m.receiveid = :receiveid ", Message.class)
                .setParameter("receiveid", receiveid)
                .getResultList();
        return resultList;
    }
    //폰넘버로 사람검색
    public List<Message> findBySendid(Long sendid) {
        return em.createQuery("select m from Message m where m.sendid = :sendid", Message.class)
                .setParameter("sendid", sendid)
                .getResultList();
    }

    public Long findOneReid(Long receiveid) {
        List<Message> messages = em.createQuery("select m from Message m where m.receiveid = :receiveid", Message.class)
                .setParameter("receiveid", receiveid)
                .getResultList();
        if (messages.isEmpty()) {
            // 해당 코드에 맞는 회원이 없는 경우 처리
            return null; // 또는 예외 처리에 맞게 다른 값을 반환
        }

        return messages.get(0).getId(); // 첫 번째 회원 반환
    }
}