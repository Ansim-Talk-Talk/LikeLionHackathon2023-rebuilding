package com.dohi.ansimtalk.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberCodeForm {
    @NotEmpty(message = "코드는 필수 입니다")
    private String code;
}
