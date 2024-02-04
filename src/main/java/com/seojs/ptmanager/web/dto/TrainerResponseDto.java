package com.seojs.ptmanager.web.dto;

import com.seojs.ptmanager.domain.message.Message;
import com.seojs.ptmanager.domain.reserve.Reserve;
import com.seojs.ptmanager.domain.trainer.Trainer;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TrainerResponseDto {
    private Long id;
    private String loginId;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> receivedMessages = new ArrayList<>();
    private List<Reserve> reserves = new ArrayList<>();

    public TrainerResponseDto(Trainer trainer) {
        this.id = trainer.getId();
        this.loginId = trainer.getLoginId();
        this.name = trainer.getName();
        this.startTime = trainer.getStartTime();
        this.endTime = trainer.getEndTime();
        this.sentMessages = trainer.getSentMessages();
        this.receivedMessages = trainer.getReceivedMessages();
        this.reserves = trainer.getReserves();
    }
}
