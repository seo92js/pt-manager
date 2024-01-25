package com.seojs.ptmanager.service;

import com.seojs.ptmanager.domain.admin.Admin;
import com.seojs.ptmanager.domain.admin.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AdminServiceTest {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AdminService adminService;

    @Test
    void save() {
        Admin admin = new Admin("id", "pw");

        Admin savedAdmin = adminService.save(admin);

        Admin findAdmin = adminService.findById(savedAdmin.getId()).get();

        assertThat(findAdmin).isEqualTo(savedAdmin);
    }

    @Test
    void findAll() {
        Admin admin1 = new Admin("1", "pw");
        Admin admin2 = new Admin("2", "pw");

        adminService.save(admin1);
        adminService.save(admin2);

        List<Admin> all = adminService.findAll();

        assertThat(all.size()).isEqualTo(2);
    }
}