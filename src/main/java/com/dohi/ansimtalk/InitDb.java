package com.dohi.ansimtalk;

import com.dohi.ansimtalk.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        /*
        initService.dbInit1();
        initService.dbInit2();

         */
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {

        }

        public void dbInit2() {

        }


        private Member createMember(String name, String phone,String type) {
            Member member = new Member();
            member.setName(name);
            member.setPhone(phone);
            member.setType(type);
            return member;
        }

    }
}

