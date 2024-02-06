package com.seojs.ptmanager.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seojs.ptmanager.service.MemberService;
import com.seojs.ptmanager.service.TicketService;
import com.seojs.ptmanager.service.TrainerService;
import com.seojs.ptmanager.web.dto.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

        mvc.perform(MockMvcRequestBuilders.patch(patchUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.patch(patchUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MemberResponseDto member = memberService.findById(memberId);

        Assertions.assertThat(member.getReserves().size()).isEqualTo(2);
        Assertions.assertThat(member.getReserves().get(0).getReserveTime()).isEqualTo(now.truncatedTo(ChronoUnit.MINUTES));
        Assertions.assertThat(member.getReserves().get(1).getReserveTime()).isEqualTo(now.truncatedTo(ChronoUnit.MINUTES));
    }
}