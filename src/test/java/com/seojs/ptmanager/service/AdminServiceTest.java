package com.seojs.ptmanager.service;

import com.seojs.ptmanager.web.dto.AdminDto;
import com.seojs.ptmanager.web.dto.AdminResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AdminServiceTest {
    @Autowired
    AdminService adminService;

    @Test
    void save() {
        AdminDto adminDto = new AdminDto("id", "name", "pw");

        Long savedId = adminService.save(adminDto);

        AdminResponseDto findAdmin = adminService.findById(savedId);

        assertThat(findAdmin.getName()).isEqualTo(adminDto.getName());
        assertThat(findAdmin.getLoginId()).isEqualTo(adminDto.getLoginId());
    }

    @Test
    void findAll() {
        AdminDto adminDto1 = new AdminDto("1", "유상철", "pw");
        AdminDto adminDto2 = new AdminDto("2", "박지성", "pw");
        AdminDto adminDto3 = new AdminDto("3", "기성용","pw");

        Long savedId1 = adminService.save(adminDto1);
        Long savedId2 = adminService.save(adminDto2);
        Long savedId3 = adminService.save(adminDto3);

        //미리 1개 넣어놔서 4개
        List<AdminResponseDto> all = adminService.findAll(null);
        assertThat(all.size()).isEqualTo(4);

        List<AdminResponseDto> find1 = adminService.findAll("성");
        Assertions.assertThat(find1.size()).isEqualTo(2);

        List<AdminResponseDto> find2 = adminService.findAll("유상");
        Assertions.assertThat(find2.size()).isEqualTo(1);

        List<AdminResponseDto> find3 = adminService.findAll("기성용");
        Assertions.assertThat(find3.size()).isEqualTo(1);
    }
}