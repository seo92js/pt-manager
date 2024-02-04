package com.seojs.ptmanager.mapper;

import com.seojs.ptmanager.domain.memberticket.MemberTicket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberTicketMapper {
    void save(MemberTicket memberTicket);

    List<MemberTicket> findByMemberId(Long memberId);
}
