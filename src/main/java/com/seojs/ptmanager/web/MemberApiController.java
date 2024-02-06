package com.seojs.ptmanager.web;

import com.seojs.ptmanager.service.MemberService;
import com.seojs.ptmanager.web.dto.MemberDto;
import com.seojs.ptmanager.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/v1/member")
    public Long save(@Validated @RequestBody MemberDto memberDto) {
        return memberService.save(memberDto);
    }

    @GetMapping("/api/v1/member/{id}")
    public MemberResponseDto findById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @GetMapping({"/api/v1/members/{name}", "/api/v1/members"})
    public List<MemberResponseDto> findAll(@PathVariable(required = false) String name) {
        return memberService.findAll(name);
    }
}
