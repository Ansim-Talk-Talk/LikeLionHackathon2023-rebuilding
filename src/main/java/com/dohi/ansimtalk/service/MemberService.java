package com.dohi.ansimtalk.service;


import com.dohi.ansimtalk.domain.Member;
import com.dohi.ansimtalk.exception.NotEnoughStockException;
import com.dohi.ansimtalk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    //수정하는애 위에는 @Transactional따로해놓자
    @Transactional
    public Long join(Member member) {
        List<Member> findMembers = memberRepository.findByPhone(member.getPhone());
        List<Member> findMembers2 = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            Member existingMember = findMembers.get(0);
            if (!findMembers2.isEmpty()) {
                if (!member.getPhone().equals(existingMember.getPhone())) {
                    throw new IllegalStateException("이미 존재하는 번호입니다."); //수정할것 -> 문제점 이름과 핸드폰번호가 따로있어도 이미 있는 번호로 인식. ->해결방법 같은 id인지 증명할것

                } else {
                    return existingMember.getId();
                }
            }
        }
        else {

            memberRepository.save(member);
        }
        return member.getId();
    }

    //수정하는애 위에는 @Transactional따로해놓자
    @Transactional
    public Long jointest(Member member) {
        List<Member> findMembers = memberRepository.findByPhone(member.getPhone());
        List<Member> findMembers2 = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            Member existingMember = findMembers.get(0);
            if (!findMembers2.isEmpty()) {
                Member existingMember2 = findMembers2.get(0);
                if (!member.getPhone().equals(existingMember.getPhone())) {
                    if (existingMember2.getId()== existingMember.getId()) {
                        throw new IllegalStateException("이미 존재하는 번호입니다."); //수정할것 -> 문제점 이름과 핸드폰번호가 따로있어도 이미 있는 번호로 인식. ->해결방법 같은 id인지 증명할것
                    }
                } else {
                    return existingMember.getId();
                }
            }
        }
        else {

            memberRepository.save(member);
        }
        return member.getId();
    }



    @Transactional
    public void codecheck(String pecode, Long bohoid) {

       Member pemember = findbycode(pecode);
       Member bohomember = memberRepository.findOne(bohoid);
         System.out.println("test");

        try {
            System.out.println("test2");

          bohomember.setCode(pecode);
            bohomember.setPartner(pemember.getId());
           memberRepository.save(bohomember);
            pemember.setPartner(bohoid);
            memberRepository.save(pemember);
        } catch (NotEnoughStockException e) {
        }

    }


    @Transactional
    public Long joinpe(Member member){
        List<Member> findMembers = memberRepository.findByPhone(member.getPhone());
        if (!findMembers.isEmpty()) {
            Member existingMember = findMembers.get(0);
            if(validateDuplicateMembercheck(member)){
                return existingMember.getId();
            } else{
                throw new IllegalStateException("이미 존재하는 번호입니다.");
            }

        }
        else {
            memberRepository.save(member);
        }
        return member.getId();
    }


    /*
    ///// 회원 조회 코드들  ///
    */
    //코드 기반으로 여러개찾기
    public List findbycodes(String randomCode){
        return memberRepository.findByCodes(randomCode);
    }
    //코드 기반으로 한명찾기 진화버전 (오류적음)
    public Member findbycode(String code){
        return memberRepository.findByCode(code);
    }
    //코드 폰중복검사+이름검사
    private void validateDuplicatePhone(Member member) {
        List<Member> findMembers = memberRepository.findByPhone(member.getPhone());
        if (!findMembers.isEmpty()) {
            Member existingMember = findMembers.get(0);
            if (!member.getName().equals(existingMember.getName())) {
                throw new IllegalStateException("이미 존재하는 번호입니다.");
            }

        }
    }



    //이름만검사
    private boolean validateDuplicateMembercheck(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            return true;
        }
        return false;
    }

    //전체 검사
    public boolean validateDuplicate(Member member) {
        List<Member> findMembersByPhone = memberRepository.findByPhone(member.getPhone());
        if (!findMembersByPhone.isEmpty()) {
            List<Member> findMembersByName = memberRepository.findByName(member.getName());
            if (!findMembersByName.isEmpty()) {
                return true; // 이름이 중복되는 회원이 이미 존재함
            }
        }
        return false; // 중복되는 회원 없음
    }

    //연결되었는지 확인
    public boolean validateDuplicatecode(Member member) {
        List<Member> findMembersByPhone = memberRepository.findByPhone(member.getPhone());
        if (!findMembersByPhone.isEmpty()) {
            List<Member> findMembersByName = memberRepository.findByName(member.getName());
            if (!findMembersByName.isEmpty()) {
                Member existingMember = findMembersByName.get(0);
                if (existingMember.getPartner() != null) {
                    return true; // p_id 값이 이미 존재함
                }
            }
        }
        return false; // 중복되는 코드보유자아님없음
    }


    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
//id로 한명조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }



}
