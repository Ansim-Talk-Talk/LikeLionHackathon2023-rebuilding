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

@Controller
@RequiredArgsConstructor
public class MemberBohoController {

    private final MemberService memberService;

    @ModelAttribute("MemberBohoForm")
    public MemberBohoForm getMemberBohoForm() {
        return new MemberBohoForm(); // 빈 생성
    }
    @ModelAttribute("MemberCodeForm")
    public MemberCodeForm getMemberCodeForm() {
        return new MemberCodeForm(); // 빈 생성
    }

    @GetMapping("/members/newboho")
    public String createForm(Model model) {
        model.addAttribute("MemberBohoForm", new MemberBohoForm());
        return "login/bohototal";
    }

    @PostMapping("/members/newboho")
    public String create(@Valid MemberBohoForm form, BindingResult result) {

            if (result.hasErrors()) { //에러가 났을경우 다시 돌아가~!
                return  "redirect:/members/bohoerror/"+"125";
            }

            Member member = new Member();
            member.setName(form.getName());
            member.setType(form.getType());
            member.setPhone(form.getPhone());
            Long m_id ;//= memberService.join(member)
        try {m_id= memberService.join(member);}
        catch (Exception e){
            return "redirect:/members/bohoerror/"+"888";
        }
            if (memberService.validateDuplicate(member)) {
                if (memberService.validateDuplicatecode(member)) {

                    return "redirect:/members/" + m_id + "/bohomain";  // 연결되어 이미 존재하는 사용자면 회원의 상세 페이지로 이동
                } else {

                    return "redirect:/members/bohocode/" + m_id;//연결 ㄴ
                }
            } else {
               // codeService.codeset(member);

                return "redirect:/members/bohocode/" + m_id; // 회원 가입 후 홈 페이지로 이동
            }
        }


    @GetMapping("/members/bohocode/{id}")
    public String showMembercodecheck(@PathVariable("id")  Long id, Model model) {

        //CodepeForm form = new CodepeForm();
        //form.setCode(code.getConnectionCode());
        Member member = memberService.findOne(id);

        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext="회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }else if (!member.getType().equals("보호자")){
            // 해당 ID가 보호자가 아니고 피보호자 계정일경우
            String errortext="보호자계정이 아닙니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";

        }

        System.out.println("test5");
        model.addAttribute("MemberCodeForm", new MemberCodeForm());
        return "login/boho5"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }

    @PostMapping("/members/bohocode/{id}")
    public String createcode(@PathVariable Long id, @Valid MemberCodeForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "login/bohototal"; // 폼 검증 에러가 발생한 경우 해당 폼 페이지로 이동
        }

        System.out.println("test4");
        System.out.println(id);
        System.out.println(form.getCode());

        try {
            memberService.codecheck(form.getCode(), id);
        } catch (IllegalArgumentException e) {
            // 예외 처리 로직 -> error할까...
            return "redirect:/members/bohocode/" + id;
        }

        return "redirect:/members/" + id + "/bohomain";
    }





    @GetMapping("/members/{id}/bohomain")
    public String showMemberstartmain(@PathVariable("id")  Long id, Model model) {
        Member member = memberService.findOne(id);

        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext="회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }else if (!member.getType().equals("보호자")){
            // 해당 ID가 보호자가 아니고 피보호자 계정일경우
            String errortext="보호자계정이 아닙니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";

        }

        model.addAttribute("member", member);
        return "login/boho6"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }
    @GetMapping("/members/{id}/bohomaintitle")
    public String showMembermaintitle(@PathVariable("id")  Long id, Model model) {
        Member member = memberService.findOne(id);
        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext="회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }else if (!member.getType().equals("보호자")){
            // 해당 ID가 보호자가 아니고 피보호자 계정일경우
            String errortext="보호자계정이 아닙니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }

        model.addAttribute("member", member);
       // model.addAttribute("MemberCodeForm", new MemberCodeForm());
        return "bo-home/bo-home-main"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }











}
