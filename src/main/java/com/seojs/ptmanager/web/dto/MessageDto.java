package com.seojs.ptmanager.web.dto;

import lombok.Getter;

@Getter
public class MessageDto {
    private String content;
    private Long sendMemberId;
    private Long receiveTrainerId;
    private Long sendTrainerId;
    private Long receiveMemberId;

    public MessageDto(String content, Long sendMemberId, Long receiveTrainerId, Long sendTrainerId, Long receiveMemberId) {
        this.content = content;
        this.sendMemberId = sendMemberId;
        this.receiveTrainerId = receiveTrainerId;
        this.sendTrainerId = sendTrainerId;
        this.receiveMemberId = receiveMemberId;
    }
}
