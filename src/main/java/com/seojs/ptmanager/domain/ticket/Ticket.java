package com.seojs.ptmanager.domain.ticket;

import lombok.Getter;

@Getter
public class Ticket {
    private Long id;
    private Integer totalNum;
    private Integer remainNum;
    private Integer price;
    private Boolean status;

    public void use() {
        this.remainNum--;

        if (remainNum.equals(0)) {
            this.status = false;
        }
    }

    public Ticket(Integer totalNum, Integer price) {
        this.totalNum = totalNum;
        this.remainNum = totalNum;
        this.price = price;
        this.status = true;
    }
}
