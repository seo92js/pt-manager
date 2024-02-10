package com.seojs.ptmanager.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(false);

        if (session == null || (session.getAttribute("loginMember") == null && session.getAttribute("loginTrainer") == null)) {
            log.info("미인증 사용자 요청");

            //로그인으로 redirect
            response.sendRedirect("로그인 화면" + requestURI);

            return false;
        }

        return true;
    }

    //추후 권한 설정을 위해
    private boolean isMember() {
        return false;
    }

    private boolean isTrainer() {
        return false;
    }

    private boolean isAdmin() {
        return false;
    }
}

