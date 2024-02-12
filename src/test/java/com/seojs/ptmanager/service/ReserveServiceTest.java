package com.seojs.ptmanager.service;

import com.seojs.ptmanager.domain.reserve.Reserve;
import com.seojs.ptmanager.exception.ReserveDuplicateEx;
import com.seojs.ptmanager.web.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThat(member.getReserves().get(0).getReserveTime()).isEqualTo(now.truncatedTo(ChronoUnit.HOURS));
        assertThat(member.getTickets().get(0).getRemainNum()).isEqualTo(4);
        assertThat(member.getTickets().get(0).getStatus()).isEqualTo(true);

//        reserveService.reserve(reserveDto);
//        reserveService.reserve(reserveDto);
//        reserveService.reserve(reserveDto);
//        reserveService.reserve(reserveDto);
//
//        member = memberService.findById(memberId);
//        assertThat(member.getTickets().get(0).getRemainNum()).isEqualTo(0);
//        assertThat(member.getTickets().get(0).getStatus()).isEqualTo(false);
    }

    @Test
    void 멤버ID와_날짜로_조회() {
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
        LocalDate localDate1 = LocalDate.of(24,1,5);
        LocalTime localTime1 = LocalTime.of(18, 0 ,0);
        LocalDateTime localDateTime1 = LocalDateTime.of(localDate1, localTime1);

        ReserveDto reserveDto1 = new ReserveDto(memberId, trainerId, ticketId, localDateTime1);
        reserveService.reserve(reserveDto1);

        LocalDate localDate2 = LocalDate.of(24,1,5);
        LocalTime localTime2 = LocalTime.of(19, 0 ,0);
        LocalDateTime localDateTime2 = LocalDateTime.of(localDate2, localTime2);

        ReserveDto reserveDto2 = new ReserveDto(memberId, trainerId, ticketId, localDateTime2);
        reserveService.reserve(reserveDto2);

        ReserveDateFindDto reserveDateFindDto = new ReserveDateFindDto(memberId, LocalDate.of(24, 1, 5));
        List<Reserve> findReserve = reserveService.findByMemberIdAndDate(reserveDateFindDto);

        assertThat(findReserve.size()).isEqualTo(2);
    }

    @Test
    void 예약_중복_테스트() {
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
        assertThat(member.getReserves().get(0).getReserveTime()).isEqualTo(now.truncatedTo(ChronoUnit.HOURS));
        assertThat(member.getTickets().get(0).getRemainNum()).isEqualTo(4);
        assertThat(member.getTickets().get(0).getStatus()).isEqualTo(true);

        assertThatThrownBy(() -> reserveService.reserve(reserveDto))
                .isInstanceOf(ReserveDuplicateEx.class);
    }
}