package com.seojs.ptmanager.service;

import com.seojs.ptmanager.web.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReserveServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    TrainerService trainerService;

    @Autowired
    TicketService ticketService;

    @Autowired
    ReserveService reserveService;

    @Test
    void 예약_후_조회() {
        MemberDto memberDto = new MemberDto("id","name","pw");
        Long memberId = memberService.save(memberDto);

        TrainerDto trainerDto = new TrainerDto("id", "트레이너", "pw");
        Long trainerId = trainerService.save(trainerDto);

        TicketDto ticketDto = new TicketDto(5, 30000);
        Long ticketId = ticketService.save(ticketDto);

        //티켓 구매
        MemberTicketDto memberTicketDto = new MemberTicketDto(memberId, ticketId);
        ticketService.buyTicket(memberTicketDto);

        //예약
        LocalDateTime now = LocalDateTime.now();
        ReserveDto reserveDto = new ReserveDto(memberId, trainerId, ticketId, now);
        reserveService.reserve(reserveDto);

        MemberResponseDto member = memberService.findById(memberId);
        assertThat(member.getReserves().get(0).getReserveTime()).isEqualTo(now.truncatedTo(ChronoUnit.MINUTES));
        assertThat(member.getTickets().get(0).getRemainNum()).isEqualTo(4);
        assertThat(member.getTickets().get(0).getStatus()).isEqualTo(true);

        reserveService.reserve(reserveDto);
        reserveService.reserve(reserveDto);
        reserveService.reserve(reserveDto);
        reserveService.reserve(reserveDto);

        member = memberService.findById(memberId);
        assertThat(member.getTickets().get(0).getRemainNum()).isEqualTo(0);
        assertThat(member.getTickets().get(0).getStatus()).isEqualTo(false);
    }
}