package com.dohi.ansimtalk.controller;

import com.dohi.ansimtalk.domain.Member;
import com.dohi.ansimtalk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Random;
@Controller
@RequiredArgsConstructor
public class MemberpeController {

    private final MemberService memberService;

    // 폼 객체 공통 제공
    @ModelAttribute("MemberpeForm")
    public MemberpeForm form() {
        return new MemberpeForm();
    }

    // 회원가입 폼
    @GetMapping("/members/newpe")
    public String createForm() {
        return "login/pebototal";
    }

    // 회원가입 처리
    @PostMapping("/members/newpe")
    public String create(@Valid @ModelAttribute("MemberpeForm") MemberpeForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errortext", "입력 오류가 발생했습니다.");
            return "login/error";
        }

        String randomCode;
        do {
            randomCode = generateRandomCode();
        } while (!memberService.findbycodes(randomCode).isEmpty());

        Member member = new Member();
        member.setName(form.getName());
        member.setType(form.getType());
        member.setPhone(form.getPhone());
        member.setCode(randomCode);

        Long m_id;
        try {
            m_id = memberService.joinpe(member);
        } catch (Exception e) {
            model.addAttribute("errortext", "회원가입 중 오류가 발생했습니다.");
            return "login/error";
        }

        member.setId(m_id);

        // 기존 회원 여부 및 코드 유무에 따라 분기
        if (memberService.validateDuplicate(member)) {
            if (memberService.validateDuplicatecode(member)) {
                return "redirect:/members/" + m_id + "/pebohomain";
            } else {
                return "redirect:/members/pebohocode/" + m_id;
            }
        } else {
            return "redirect:/members/pebohocode/" + m_id;
        }
    }

    // 피보호자 로그인 전 코드 확인 페이지
    @GetMapping("/members/pebohocode/{id}")
    public String showMemberDetailsLogin(@PathVariable Long id, Model model) {
        Member member = memberService.findOne(id);

        if (!isValidPeMember(member, model)) {
            return "login/error";
        }

        model.addAttribute("member", member);
        return (member.getPartner() != null) ? "login/peboho6_1" : "login/peboho5";
    }

    // 피보호자 메인
    @GetMapping("/members/{id}/pebohomain")
    public String showMemberMain(@PathVariable Long id, Model model) {
        Member member = memberService.findOne(id);

        if (!isValidPeMember(member, model)) {
            return "login/error";
        }

        model.addAttribute("member", member);
        return "login/peboho6";
    }

    // 피보호자 메인 타이틀 화면
    @GetMapping("/members/{id}/pebohomaintitle")
    public String showMemberMainTitle(@PathVariable Long id, Model model) {
        Member member = memberService.findOne(id);

        if (!isValidPeMember(member, model)) {
            return "login/error";
        }

        model.addAttribute("member", member);
        return "pe-home/pe-home-main";
    }

    // 랜덤 코드 생성기
    private String generateRandomCode() {
        String characters = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ123456789!@#?";
        StringBuilder codeBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(index));
        }

        return codeBuilder.toString();
    }

    // 공통 피보호자 검증 로직
    private boolean isValidPeMember(Member member, Model model) {
        if (member == null) {
            model.addAttribute("errortext", "회원 정보가 없습니다.");
            return false;
        }
        if (!"피보호자".equals(member.getType())) {
            model.addAttribute("errortext", "피보호자 계정이 아닙니다.");
            return false;
        }
        return true;
    }
}
