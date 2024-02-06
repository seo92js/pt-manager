package com.seojs.ptmanager;

import com.seojs.ptmanager.web.interceptor.LogInterceptor;
import com.seojs.ptmanager.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/api/v1/member", "/api/v1/trainer",
                        "/api/v1/login/member", "/api/v1/login/trainer",
                        "/api/v1/logout",
                        "/css/**", "/*.ico", "/error"
                );
    }
}
