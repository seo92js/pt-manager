package com.seojs.ptmanager.service;

import com.seojs.ptmanager.domain.member.Member;
import com.seojs.ptmanager.domain.member.MemberRepository;
import com.seojs.ptmanager.domain.trainer.Trainer;
import com.seojs.ptmanager.domain.trainer.TrainerRepository;
import com.seojs.ptmanager.web.dto.LoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    LoginService loginService;

    @Test
    void 멤버_로그인_성공() {
        Member member = new Member("idid", "name", "password");
        memberRepository.save(member);
        LoginDto loginDto = new LoginDto(member.getLoginId(), member.getPassword());

        Member result = loginService.memberLogin(loginDto);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(member.getName());
        assertThat(result.getLoginId()).isEqualTo(member.getLoginId());
        assertThat(result.getPassword()).isEqualTo(member.getPassword());
    }

    @Test
    void 멤버_로그인_실패() {
        Member member = new Member("idid", "name", "password");
        memberRepository.save(member);
        LoginDto loginDto = new LoginDto(member.getLoginId(), "pass");

        Member result = loginService.memberLogin(loginDto);

        assertThat(result).isNull();
    }

    @Test
    void 트레이너_로그인_성공() {
        Trainer trainer = new Trainer("idid", "name", "password");
        trainerRepository.save(trainer);
        LoginDto loginDto = new LoginDto(trainer.getLoginId(), trainer.getPassword());

        Trainer result = loginService.trainerLogin(loginDto);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(trainer.getName());
        assertThat(result.getLoginId()).isEqualTo(trainer.getLoginId());
        assertThat(result.getPassword()).isEqualTo(trainer.getPassword());
    }

    @Test
    void 트레이너_로그인_실패() {
        Trainer trainer = new Trainer("idid", "name", "password");
        trainerRepository.save(trainer);
        LoginDto loginDto = new LoginDto(trainer.getLoginId(), "pass");

        Trainer result = loginService.trainerLogin(loginDto);

        assertThat(result).isNull();
    }
}