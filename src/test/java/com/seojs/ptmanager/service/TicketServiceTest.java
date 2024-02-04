package com.seojs.ptmanager.service;

import com.seojs.ptmanager.exception.TicketDuplicateEx;
import com.seojs.ptmanager.web.dto.TicketDto;
import com.seojs.ptmanager.web.dto.TicketResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class TicketServiceTest {
    @Autowired
    TicketService ticketService;

    @Test
    void save() {
        TicketDto ticketDto = new TicketDto(5, 30000);

        Long savedId = ticketService.save(ticketDto);

        List<TicketResponseDto> all = ticketService.findAll();

        assertThat(all.get(0).getTotalNum()).isEqualTo(ticketDto.getTotalNum());
        assertThat(all.get(0).getPrice()).isEqualTo(ticketDto.getPrice());
        assertThat(all.get(0).getStatus()).isEqualTo(true);
    }

    @Test
    void findById() {
        TicketDto ticketDto = new TicketDto(5, 5000);
        Long savedId = ticketService.save(ticketDto);

        TicketResponseDto findTicket = ticketService.findById(savedId);

        assertThat(findTicket.getId()).isEqualTo(savedId);
        assertThat(findTicket.getTotalNum()).isEqualTo(5);
        assertThat(findTicket.getPrice()).isEqualTo(5000);
        assertThat(findTicket.getStatus()).isEqualTo(true);
    }

    @Test
    void findAll() {
        TicketDto ticketDto1 = new TicketDto(5, 30000);
        TicketDto ticketDto2 = new TicketDto(10, 50000);
        TicketDto ticketDto3 = new TicketDto(15, 70000);

        Long savedId1 = ticketService.save(ticketDto1);
        Long savedId2 = ticketService.save(ticketDto2);
        Long savedId3 = ticketService.save(ticketDto3);

        List<TicketResponseDto> all = ticketService.findAll();

        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    void 이용권_중복검사() {
        TicketDto ticketDto1 = new TicketDto(5, 30000);

        Long savedId1 = ticketService.save(ticketDto1);

        TicketDto ticketDto2 = new TicketDto(5, 50000);

        assertThatThrownBy(() -> ticketService.save(ticketDto2))
                .isInstanceOf(TicketDuplicateEx.class);
    }
}