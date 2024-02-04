package com.seojs.ptmanager.domain.member;

import com.seojs.ptmanager.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final MemberMapper memberMapper;

    public Long save(Member member) {
        memberMapper.save(member);
        return member.getId();
    }

    public Optional<Member> findById(Long id) {
        return memberMapper.findById(id);
    }

    public Optional<Member> findByLoginId(String loginid) {
        return memberMapper.findByLoginId(loginid);
    }

    public List<Member> findAll(String name) {
        return memberMapper.findAll(name);
    }
}
