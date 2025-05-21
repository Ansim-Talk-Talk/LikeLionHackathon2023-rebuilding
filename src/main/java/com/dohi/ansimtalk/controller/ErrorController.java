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

@Controller
@RequiredArgsConstructor
public class ErrorController {

    private final MemberService memberService;

    @GetMapping("/members/pebohoerror/{errorcode}")
    public String catchpebohoerror(@PathVariable("errorcode")  Long errorcode, Model model) {
        String errortext;
        if(errorcode == 888){
            errortext = "동일한 번호 가입자존재";
        }else if (errorcode == 125){
            errortext = "회원가입을 다시시도해주세요";
        }
        else {
            errortext="비정상 루트";
        }
        model.addAttribute("errortext", errortext);
        return "login/error";
    }

    @GetMapping("/members/bohoerror/{errorcode}")
    public String catchbohoerror(@PathVariable("errorcode")  Long errorcode, Model model) {
        String errortext;
        if(errorcode == 888){
            errortext = "동일한 번호 가입자존재";
        }else if (errorcode == 125){
            errortext = "회원가입을 다시시도해주세요";
        }
        else {
            errortext="비정상 루트";
        }
        model.addAttribute("errortext", errortext);
        return "login/error";
    }
}
