package com.dohi.ansimtalk.user.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserSignupRequest {

        @NotBlank(message = "이름은 필수입니다.")
        private String name;

        @NotBlank(message = "사용자 유형을 선택하세요.")  // 예: 보호자 / 피보호자
        private String type;

        @NotBlank(message = "전화번호는 필수입니다.")
        @Pattern(regexp = "^010\\d{8}$", message = "정확한 전화번호를 입력하세요.")
        private String phone;



}
