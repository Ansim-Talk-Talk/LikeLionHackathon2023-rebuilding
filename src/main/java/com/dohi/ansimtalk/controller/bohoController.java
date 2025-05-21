package com.dohi.ansimtalk.controller;

import com.dohi.ansimtalk.domain.Member;
import com.dohi.ansimtalk.service.MemberService;
import com.dohi.ansimtalk.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class bohoController {


    private final MemberService memberService;
    private final MessageService messageService;
//BohoCotroller = 해커톤 마감하기 위해 매핑만 급하게 끝낸
    @GetMapping("/members/{id}/log.html")
    public String showMemberloghtml(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);
        model.addAttribute("member", member);
        if (member == null) {
            String errortext = "회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        } else if (!member.getType().equals("보호자")) {
            return "pe-home/log.html";
        }


        // model.addAttribute("MemberCodeForm", new MemberCodeForm());
        return "bo-home/log.html"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }

    @GetMapping("/members/{id}/locate.html")
    public String showMemberlocatehtml(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);

        model.addAttribute("member", member);
        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext = "회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        } else if (!member.getType().equals("보호자")) {
            // 해당 ID가 보호자가 아니고 피보호자 계정일경우
            String errortext = "보호자계정이 아닙니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }
        return "bo-home/locate.html"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }

    @GetMapping("/members/{id}/message.html")
    public String showMembermessgehtml(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);
        model.addAttribute("member", member);
        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext = "회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        } else if (!member.getType().equals("보호자")) {
            return "pe-home/message.html";
        }
        return "bo-home/message.html"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }

    @GetMapping("/members/{id}/todolist.html")
    public String showMembertodolisthtml(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);

        model.addAttribute("member", member);
        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext = "회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        } else if (!member.getType().equals("보호자")) {
            return "pe-home/todolist.html";
        }

        // model.addAttribute("MemberCodeForm", new MemberCodeForm());
        return "bo-home/todolist.html"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }


    @GetMapping("/members/{id}/todolist2.html")
    public String showMembertodolist2html(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);

        model.addAttribute("member", member);
        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext = "회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        } else if (!member.getType().equals("보호자")) {
            return "pe-home/todolist2.html";
        }
            return "bo-home/todolist2.html"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }


    @GetMapping("/members/{id}/mypage.html")
    public String showMembermypagehtml(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);

        model.addAttribute("member", member);
        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext = "회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        } else if (!member.getType().equals("보호자")) {
            return "pe-home/mypage.html";
        }
        return "bo-home/mypage.html"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }

    @GetMapping("/members/{id}/emoticon.html")
    public String showMemberemoticonhtml(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);

        model.addAttribute("member", member);
        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext = "회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        } else if (!member.getType().equals("보호자")) {
            return "pe-home/emoticon.html";
        }
       return "bo-home/emoticon.html"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }


    @GetMapping("/members/{id}/119.html")
    public String showMembermain119html(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);
        model.addAttribute("member", member);
        if (member == null) {
            String errortext = "회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        } else if (!member.getType().equals("피보호자")) {
            String errortext = "회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }
        return "pe-home/119.html"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }
    @GetMapping("/members/{id}/pe-home-main.html")
    public String showMembermainhtml(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);

        model.addAttribute("member", member);
        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext = "회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        } else if (!member.getType().equals("보호자")) {
            return "pe-home/pe-home-main.html";
        }

        // model.addAttribute("MemberCodeForm", new MemberCodeForm());
        return "bo-home/pe-home-main.html"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }


}