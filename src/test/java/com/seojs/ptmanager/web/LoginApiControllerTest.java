package com.seojs.ptmanager.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seojs.ptmanager.domain.member.Member;
import com.seojs.ptmanager.domain.member.MemberRepository;
import com.seojs.ptmanager.domain.trainer.Trainer;
import com.seojs.ptmanager.domain.trainer.TrainerRepository;
import com.seojs.ptmanager.web.dto.LoginDto;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LoginApiControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TrainerRepository trainerRepository;

    Long memberId;
    Long trainerId;

    @BeforeEach
    void setUp() {
        Member member = new Member("idid", "name", "password");
        memberId = memberRepository.save(member);
        Trainer trainer = new Trainer("idid", "name", "password");
        trainerId = trainerRepository.save(trainer);
    }

    @Test
    void 멤버_로그인_성공() throws Exception {
        LoginDto loginDto = new LoginDto("idid", "password");

        String postUrl = "/api/v1/login/member";

        MvcResult result = mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn();

        HttpSession session = result.getRequest().getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");

        assertThat(result.getResponse().getContentAsString()).isEqualTo("redirect:/");
        assertThat(loginMember.getLoginId()).isEqualTo("idid");
        assertThat(loginMember.getName()).isEqualTo("name");
    }

    @Test
    void 멤버_로그인_실패() throws Exception {
        LoginDto loginDto = new LoginDto("idid", "wrongpassword");

        String postUrl = "/api/v1/login/member";

        MvcResult result = mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn();

        HttpSession session = result.getRequest().getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");

        assertThat(result.getResponse().getContentAsString()).isEqualTo("로그인 화면");
        assertThat(loginMember).isNull();
    }

    @Test
    void 멤버_로그아웃() throws Exception {
        //로그인
        LoginDto loginDto = new LoginDto("idid", "password");

        String postUrl = "/api/v1/login/member";

        MvcResult result = mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn();

        HttpSession session = result.getRequest().getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");

        assertThat(result.getResponse().getContentAsString()).isEqualTo("redirect:/");
        assertThat(loginMember.getLoginId()).isEqualTo("idid");
        assertThat(loginMember.getName()).isEqualTo("name");

        //로그아웃
        postUrl = "/api/v1/logout";

        MvcResult resultLogout = mvc.perform(post(postUrl))
                .andExpect(status().isOk())
                .andReturn();

        session = resultLogout.getRequest().getSession();
        loginMember = (Member) session.getAttribute("loginMember");

        assertThat(result.getResponse().getContentAsString()).isEqualTo("redirect:/");
        assertThat(loginMember).isNull();
    }

    @Test
    void 트레이너_로그인_성공() throws Exception {
        LoginDto loginDto = new LoginDto("idid", "password");

        String postUrl = "/api/v1/login/trainer";

        MvcResult result = mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn();

        HttpSession session = result.getRequest().getSession();
        Trainer loginTrainer = (Trainer) session.getAttribute("loginTrainer");

        assertThat(result.getResponse().getContentAsString()).isEqualTo("redirect:/");
        assertThat(loginTrainer.getLoginId()).isEqualTo("idid");
        assertThat(loginTrainer.getName()).isEqualTo("name");
    }

    @Test
    void 트레이너_로그인_실패() throws Exception {
        LoginDto loginDto = new LoginDto("idid", "wrongpassword");

        String postUrl = "/api/v1/login/trainer";

        MvcResult result = mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn();

        HttpSession session = result.getRequest().getSession();
        Trainer loginTrainer = (Trainer) session.getAttribute("loginTrainer");

        assertThat(result.getResponse().getContentAsString()).isEqualTo("로그인 화면");
        assertThat(loginTrainer).isNull();
    }

    @Test
    void 트레이너_로그아웃() throws Exception {
        //로그인
        LoginDto loginDto = new LoginDto("idid", "password");

        String postUrl = "/api/v1/login/trainer";

        MvcResult result = mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn();

        HttpSession session = result.getRequest().getSession();
        Trainer loginTrainer = (Trainer) session.getAttribute("loginTrainer");

        assertThat(result.getResponse().getContentAsString()).isEqualTo("redirect:/");
        assertThat(loginTrainer.getLoginId()).isEqualTo("idid");
        assertThat(loginTrainer.getName()).isEqualTo("name");

        //로그아웃
        postUrl = "/api/v1/logout";

        MvcResult resultLogout = mvc.perform(post(postUrl))
                .andExpect(status().isOk())
                .andReturn();

        session = resultLogout.getRequest().getSession();
        loginTrainer = (Trainer) session.getAttribute("loginTrainer");

        assertThat(result.getResponse().getContentAsString()).isEqualTo("redirect:/");
        assertThat(loginTrainer).isNull();
    }
}