package com.dohi.ansimtalk.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;
    private String type = "보호자";
    @NotEmpty(message = "번호는 필수입니다")
    private String phone;
}
