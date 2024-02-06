package com.seojs.ptmanager.web.dto;

import lombok.Getter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Getter
public class TrainerTimeUpdateDto {
    private LocalTime startTime;
    private LocalTime endTime;

    public TrainerTimeUpdateDto(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime.truncatedTo(ChronoUnit.MINUTES);
        this.endTime = endTime.truncatedTo(ChronoUnit.MINUTES);
    }
}
