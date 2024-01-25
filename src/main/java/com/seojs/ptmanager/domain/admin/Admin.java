package com.seojs.ptmanager.domain.admin;

import lombok.Data;

@Data
public class Admin {
    private Long id;
    private String loginId;
    private String password;

    public Admin(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
