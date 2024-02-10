package com.seojs.ptmanager.web.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReserveFindDto {
    private Long id;
    private LocalDate localDate;

    public ReserveFindDto(Long id, LocalDate localDate) {
        this.id = id;
        this.localDate = localDate;
    }
}
