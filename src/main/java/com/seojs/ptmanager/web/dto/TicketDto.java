package com.seojs.ptmanager.web.dto;

import lombok.Getter;

@Getter
public class TicketDto {
    private Integer totalNum;
    private Integer price;

    public TicketDto(Integer totalNum, Integer price) {
        this.totalNum = totalNum;
        this.price = price;
    }
}
