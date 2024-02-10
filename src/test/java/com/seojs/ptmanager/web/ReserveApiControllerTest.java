package com.seojs.ptmanager.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seojs.ptmanager.service.MemberService;
import com.seojs.ptmanager.service.TicketService;
import com.seojs.ptmanager.service.TrainerService;
import com.seojs.ptmanager.web.dto.*;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ReserveApiControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    MemberService memberService;

    @Autowired
    TrainerService trainerService;

    @Autowired
    TicketService ticketService;

    @Autowired
    ObjectMapper objectMapper;

    Long memberId;
    Long trainerId;
    Long ticketId;

    @BeforeEach
    void setUp() {
        MemberDto memberDto = new MemberDto("id", "name", "password");
        memberId = memberService.save(memberDto);

        TrainerDto trainerDto = new TrainerDto("id", "name", "password");
        trainerId = trainerService.save(trainerDto);

        TicketDto ticketDto = new TicketDto(5, 10000);
        ticketId = ticketService.save(ticketDto);

        MemberTicketDto memberTicketDto = new MemberTicketDto(memberId, ticketId);
        ticketService.buyTicket(memberTicketDto);
    }

    @Test
    void 예약() throws Exception {
        String patchUrl = "/api/v1/reserve";
        LocalDateTime now = LocalDateTime.now();
        ReserveDto reserveDto = new ReserveDto(memberId, trainerId, ticketId, now);

        mvc.perform(patch(patchUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserveDto)))
                .andExpect(status().isOk());

        mvc.perform(patch(patchUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserveDto)))
                .andExpect(status().isOk());

        MemberResponseDto member = memberService.findById(memberId);

        Assertions.assertThat(member.getReserves().size()).isEqualTo(2);
        Assertions.assertThat(member.getReserves().get(0).getReserveTime()).isEqualTo(now.truncatedTo(ChronoUnit.HOURS));
        Assertions.assertThat(member.getReserves().get(1).getReserveTime()).isEqualTo(now.truncatedTo(ChronoUnit.HOURS));
    }

    @Test
    void 멤버ID와_날짜로_조회() throws Exception {
        //예약
        String patchUrl = "/api/v1/reserve";
        LocalDate localDate = LocalDate.of(24,1,5);
        LocalTime localTime = LocalTime.of(18, 0 ,0);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        ReserveDto reserveDto = new ReserveDto(memberId, trainerId, ticketId, localDateTime);

        mvc.perform(patch(patchUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserveDto)))
                .andExpect(status().isOk());

        localDate = LocalDate.of(24,1,5);
        localTime = LocalTime.of(20, 0 ,0);
        localDateTime = LocalDateTime.of(localDate, localTime);

        reserveDto = new ReserveDto(memberId, trainerId, ticketId, localDateTime);

        mvc.perform(patch(patchUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserveDto)))
                .andExpect(status().isOk());

        localDate = LocalDate.of(24,1,6);
        localTime = LocalTime.of(20, 0 ,0);
        localDateTime = LocalDateTime.of(localDate, localTime);

        reserveDto = new ReserveDto(memberId, trainerId, ticketId, localDateTime);

        mvc.perform(patch(patchUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserveDto)))
                .andExpect(status().isOk());

        //조회
        ReserveFindDto reserveFindDto = new ReserveFindDto(memberId, LocalDate.of(24, 1, 5));

        String getUrl = "/api/v1/reserve/member";
        mvc.perform(get(getUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reserveFindDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }
}