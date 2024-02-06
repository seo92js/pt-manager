package com.seojs.ptmanager.web.dto;

import lombok.Getter;

@Getter
public class LoginDto {
    private String loginId;
    private String password;

    public LoginDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
