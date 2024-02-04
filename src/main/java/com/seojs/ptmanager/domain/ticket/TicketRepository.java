package com.seojs.ptmanager.domain.ticket;

import com.seojs.ptmanager.mapper.TicketMapper;
import com.seojs.ptmanager.web.dto.TicketUpdateNumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketRepository {
    private final TicketMapper ticketMapper;

    public Long save(Ticket ticket) {
        ticketMapper.save(ticket);
        return ticket.getId();
    }

    public Optional<Ticket> findById(Long id) {
        return ticketMapper.findById(id);
    }

    public Optional<Ticket> findByTotalNum(Integer totalNum) {
        return ticketMapper.findByTotalNum(totalNum);
    }

    public List<Ticket> findAll() {
        return ticketMapper.findAll();
    }

    public Long updateNum(Long id, TicketUpdateNumDto ticketUpdateNumDto) {
        ticketMapper.updateNum(id, ticketUpdateNumDto);
        return id;
    }
}
