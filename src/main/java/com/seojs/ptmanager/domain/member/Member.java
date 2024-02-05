package com.seojs.ptmanager.domain.member;

import com.seojs.ptmanager.domain.message.Message;
import com.seojs.ptmanager.domain.reserve.Reserve;
import com.seojs.ptmanager.domain.ticket.Ticket;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class Member {
    private Long id;
    private String loginId;
    private String name;
    private String password;
    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> receivedMessages = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
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

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void addAllTicket(List<Ticket> tickets) {
        this.tickets.clear();
        this.tickets.addAll(tickets);
    }

    public void addReserve(Reserve reserve) {
        this.reserves.add(reserve);
    }

    public void addAllReserve(List<Reserve> reserves) {
        this.reserves.clear();
        this.reserves.addAll(reserves);
    }

    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
