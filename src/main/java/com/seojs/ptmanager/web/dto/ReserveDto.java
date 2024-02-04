package com.seojs.ptmanager.web.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReserveDto {
    private Long memberId;
    private Long trainerId;
    private Long ticketId;
    private LocalDateTime reserveTime;

    public ReserveDto(Long memberId, Long trainerId, Long ticketId, LocalDateTime reserveTime) {
        this.memberId = memberId;
        this.trainerId = trainerId;
        this.ticketId = ticketId;
        this.reserveTime = reserveTime;
    }
}
