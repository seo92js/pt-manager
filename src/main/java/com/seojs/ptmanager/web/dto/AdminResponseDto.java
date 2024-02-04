package com.seojs.ptmanager.web.dto;

import com.seojs.ptmanager.domain.admin.Admin;
import lombok.Getter;

@Getter
public class AdminResponseDto {
    private String loginId;
    private String name;

    public AdminResponseDto(Admin admin) {
        this.loginId = admin.getLoginId();
        this.name = admin.getName();
    }
}
