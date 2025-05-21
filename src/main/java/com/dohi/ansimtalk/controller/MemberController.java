package com.dohi.ansimtalk.controller;

import com.dohi.ansimtalk.domain.Member;
import com.dohi.ansimtalk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String create(Model model) {
        return "/login/mode";
    }

}
