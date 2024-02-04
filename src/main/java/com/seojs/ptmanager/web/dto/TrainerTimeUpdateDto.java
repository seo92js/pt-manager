package com.seojs.ptmanager.web.dto;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TrainerTimeUpdateDto {
    private LocalTime startTime;
    private LocalTime endTime;

    public TrainerTimeUpdateDto(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
