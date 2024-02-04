package com.seojs.ptmanager.domain.message;

import com.seojs.ptmanager.domain.member.Member;
import com.seojs.ptmanager.domain.trainer.Trainer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Message {
    private Long id;
    private String content;
    private Boolean isRead;
    private Member sendMember;
    private Trainer receiveTrainer;
    private Trainer sendTrainer;
    private Member receiveMember;
    private LocalDateTime createdDate;

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Message(String content, Member sendMember, Trainer receiveTrainer) {
        this.content = content;
        this.isRead = false;
        this.sendMember = sendMember;
        this.receiveTrainer = receiveTrainer;
        this.sendTrainer = null;
        this.receiveMember = null;
    }

    public Message(String content, Trainer sendTrainer, Member receiveMember) {
        this.content = content;
        this.isRead = false;
        this.sendMember = null;
        this.receiveTrainer = null;
        this.sendTrainer = sendTrainer;
        this.receiveMember = receiveMember;
    }
}
