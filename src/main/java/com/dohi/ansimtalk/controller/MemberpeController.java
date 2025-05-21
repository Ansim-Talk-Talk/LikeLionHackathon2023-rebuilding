package com.dohi.ansimtalk.controller;

import com.dohi.ansimtalk.domain.Member;
import com.dohi.ansimtalk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class MemberpeController {

    private final MemberService memberService;
    @GetMapping("/members/newpe")
    public String createForm(Model model) {
        model.addAttribute("MemberpeForm", new MemberpeForm());
        return "login/pebototal";
    }
    ////



    @PostMapping("/members/newpe")
    public String create(@Valid MemberpeForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/members/pebohoerror/"+"125";
        }
        String randomCode;
        List<Member> existingCodes;
        do {
            randomCode = generateRandomCode();
            existingCodes = memberService.findbycodes(randomCode);

        } while (!existingCodes.isEmpty());

        Member member = new Member();
        member.setName(form.getName());
        member.setType(form.getType());
        member.setPhone(form.getPhone());
        member.setCode(randomCode);
        Long m_id ;
               try {m_id= memberService.joinpe(member);}
               catch (Exception e){
                   return "redirect:/members/pebohoerror/"+"888";
               }
        member.setId(m_id);
        if (memberService.validateDuplicate(member)) {
            if (memberService.validateDuplicatecode(member)) {

                return "redirect:/members/" + m_id + "/pebohomain";  // 연결되어 이미 존재하는 사용자면 회원의 상세 페이지로 이동
            } else {
                return "redirect:/members/pebohocode/" + m_id;//연결되어있지않아서 코드 페이지로 넘어가자!
            }
        } else {
            return "redirect:/members/pebohocode/" + m_id; // 회원 가입 후 코드페이지로 이동
        }

    }


    private String generateRandomCode() {
        // 가능한 문자와 숫자를 정의
        String characters = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ123456789!@#?";

        // 랜덤 코드 생성
        StringBuilder codeBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(index));
        }
        return codeBuilder.toString();
    }



    @GetMapping("/members/pebohocode/{id}")
    public String showMemberDetailslogin(@PathVariable Long id, Model model) {
        Member member = memberService.findOne(id);
        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext="회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }else if (!member.getType().equals("피보호자")){
            // 해당 ID가 보호자가 아니고 피보호자 계정일경우
            String errortext="피보호자계정이 아닙니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }

        model.addAttribute("member", member);
        if(member.getPartner()!=null){
            return "login/peboho6_1";//비정상경로로왔지만 피보호자고 회원이 있을경우 로그인으로 보내줌
        }
        return "login/peboho5"; // 정상경로로왔을경우
    }


    @GetMapping("/members/{id}/pebohomain")
    public String showMembermain(@PathVariable("id")  Long id, Model model) {
        Member member = memberService.findOne(id);

        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext="회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }else if (!member.getType().equals("피보호자")){
            // 해당 ID가 보호자가 아니고 피보호자 계정일경우
            String errortext="피보호자계정이 아닙니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }

        model.addAttribute("member", member);
        return "login/peboho6"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }

    @GetMapping("/members/{id}/pebohomaintitle")
    public String showMembermain2(@PathVariable("id")  Long id, Model model) {

        Member member = memberService.findOne(id);
        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext="회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }else if (!member.getType().equals("피보호자")){
            // 해당 ID가 보호자가 아니고 피보호자 계정일경우
            String errortext="피보호자계정이 아닙니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }
        model.addAttribute("member", member);
        return "pe-home/pe-home-main"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }

}
