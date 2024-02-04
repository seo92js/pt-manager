package com.seojs.ptmanager.web.dto;

import lombok.Getter;

@Getter
public class MemberDto {
    private String loginId;
    private String name;
    private String password;

    public MemberDto(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
