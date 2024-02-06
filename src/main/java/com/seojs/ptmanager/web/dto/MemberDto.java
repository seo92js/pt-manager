package com.seojs.ptmanager.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberDto {
    @NotBlank
    @Size(min = 3, max = 15, message = "3글자 ~ 15글자")
    @Pattern(regexp = "^[a-z0-9]*$", message = "알파벳 소문자(a-z), 숫자(0-9)만 입력 가능합니다.")
    private String loginId;
    @NotBlank
    private String name;
    @NotBlank
    @Size(min = 6, max = 15, message = "6글자 ~ 15글자")
    @Pattern(regexp = "^[a-z0-9]*$", message = "알파벳 소문자(a-z), 숫자(0-9)만 입력 가능합니다.")
    private String password;

    public MemberDto(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
