package com.seojs.ptmanager.web.dto;

import com.seojs.ptmanager.domain.ticket.Ticket;
import lombok.Getter;

@Getter
public class TicketResponseDto {
    private Long id;
    private Integer totalNum;
    private Integer price;
    private Boolean status;

    public TicketResponseDto(Ticket ticket) {
        this.id = ticket.getId();
        this.totalNum = ticket.getTotalNum();
        this.price = ticket.getPrice();
        this.status = ticket.getStatus();
    }
}
