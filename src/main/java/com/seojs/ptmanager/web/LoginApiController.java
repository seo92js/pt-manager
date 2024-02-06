package com.seojs.ptmanager.web;

import com.seojs.ptmanager.domain.member.Member;
import com.seojs.ptmanager.domain.trainer.Trainer;
import com.seojs.ptmanager.service.LoginService;
import com.seojs.ptmanager.web.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginApiController {
    private final LoginService loginService;

    @PostMapping("/api/v1/login/member")
    public String memberLogin(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        Member member = loginService.memberLogin(loginDto);

        //로그인 실패
        if (member == null) {
            return "로그인 화면";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", member);

        //로그인 성공
        return "redirect:/";
    }

    @PostMapping("/api/v1/login/trainer")
    public String trainerLogin(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        Trainer trainer = loginService.trainerLogin(loginDto);

        //로그인 실패
        if (trainer == null) {
            return "로그인 화면";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginTrainer", trainer);

        //로그인 성공
        return "redirect:/";
    }

    @PostMapping("/api/v1/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
