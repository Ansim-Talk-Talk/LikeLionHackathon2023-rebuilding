package com.dohi.ansimtalk.user.controller;

import com.dohi.ansimtalk.user.controller.dto.UserSignupRequest;
import com.dohi.ansimtalk.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("userSignupRequest") UserSignupRequest request,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "signup/form";
        }

        userService.register(request); // DTO를 엔티티로 변환하여 저장
        return "redirect:/signup/success";
    }



}
