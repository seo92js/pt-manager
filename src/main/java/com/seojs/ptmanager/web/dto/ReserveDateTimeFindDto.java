package com.seojs.ptmanager.web.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReserveDateTimeFindDto {
    private Long id;
    private LocalDateTime localDateTime;

    public ReserveDateTimeFindDto(Long id, LocalDateTime localDateTime) {
        this.id = id;
        this.localDateTime = localDateTime;
    }
}
