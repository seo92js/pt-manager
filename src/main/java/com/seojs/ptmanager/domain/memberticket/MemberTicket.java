package com.seojs.ptmanager.domain.memberticket;

import lombok.Getter;

@Getter
public class MemberTicket {
    private Long id;
    private Long memberId;
    private Long ticketId;

    public MemberTicket(Long memberId, Long ticketId) {
        this.memberId = memberId;
        this.ticketId = ticketId;
    }
}
