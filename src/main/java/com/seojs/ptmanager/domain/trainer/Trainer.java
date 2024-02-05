package com.seojs.ptmanager.domain.trainer;

import com.seojs.ptmanager.domain.message.Message;
import com.seojs.ptmanager.domain.reserve.Reserve;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class Trainer {
    private Long id;
    private String loginId;
    private String name;
    private String password;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> receivedMessages = new ArrayList<>();
    private List<Reserve> reserves = new ArrayList<>();

    public void addSentMessage(Message message) {
        this.sentMessages.add(message);
    }

    public void addReceivedMessage(Message message) {
        this.receivedMessages.add(message);
    }

    public void addAllSentMessage(List<Message> messages) {
        this.sentMessages.clear();
        this.sentMessages.addAll(messages);
    }

    public void addAllReceivedMessage(List<Message> messages) {
        this.receivedMessages.clear();
        this.receivedMessages.addAll(messages);
    }

    public void addAllReserve(List<Reserve> reserves) {
        this.reserves.clear();
        this.reserves.addAll(reserves);
    }

    public Trainer(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.startTime = LocalTime.MIN;
        this.endTime = LocalTime.MAX;
    }
}
