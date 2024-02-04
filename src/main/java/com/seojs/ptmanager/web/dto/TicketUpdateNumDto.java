package com.seojs.ptmanager.web.dto;

import lombok.Getter;

@Getter
public class TicketUpdateNumDto {
    private Integer remainNum;
    private boolean status;

    public TicketUpdateNumDto(Integer remainNum, boolean status) {
        this.remainNum = remainNum;
        this.status = status;
    }
}
