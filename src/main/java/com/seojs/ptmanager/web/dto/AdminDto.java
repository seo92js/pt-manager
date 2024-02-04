package com.seojs.ptmanager.web.dto;

import lombok.Getter;

@Getter
public class AdminDto {
    private String loginId;
    private String name;
    private String password;

    public AdminDto(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
