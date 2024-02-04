package com.seojs.ptmanager.service;

import com.seojs.ptmanager.domain.ticket.Ticket;
import com.seojs.ptmanager.domain.ticket.TicketRepository;
import com.seojs.ptmanager.exception.TicketDuplicateEx;
import com.seojs.ptmanager.web.dto.TicketDto;
import com.seojs.ptmanager.web.dto.TicketResponseDto;
import com.seojs.ptmanager.web.dto.TicketUpdateNumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    @Transactional
    public Long save(TicketDto ticketDto) {
        ticketDuplicateCheck(ticketDto.getTotalNum());

        return ticketRepository.save(new Ticket(ticketDto.getTotalNum(), ticketDto.getPrice()));
    }

    @Transactional
    public TicketResponseDto findById(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow();

        return new TicketResponseDto(ticket);
    }

    @Transactional
    public List<TicketResponseDto> findAll() {
        return ticketRepository.findAll().stream()
                .map(TicketResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long updateRemainNum(Long id, TicketUpdateNumDto ticketUpdateRemainNumDto) {
        return ticketRepository.updateNum(id, ticketUpdateRemainNumDto);
    }

    void ticketDuplicateCheck(Integer totalNum) {
        ticketRepository.findByTotalNum(totalNum).ifPresent(existingTicket -> {
            throw new TicketDuplicateEx("중복 된 이용권이 있습니다. totalNum=" + totalNum);
        });
    }
}
