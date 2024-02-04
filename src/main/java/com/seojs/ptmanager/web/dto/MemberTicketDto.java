package com.seojs.ptmanager.web.dto;

import lombok.Getter;

@Getter
public class MemberTicketDto {
    private Long memberId;
    private Long ticketId;

    public MemberTicketDto(Long memberId, Long ticketId) {
        this.memberId = memberId;
        this.ticketId = ticketId;
    }
}
