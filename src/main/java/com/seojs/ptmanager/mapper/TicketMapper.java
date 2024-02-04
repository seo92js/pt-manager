package com.seojs.ptmanager.mapper;

import com.seojs.ptmanager.domain.ticket.Ticket;
import com.seojs.ptmanager.web.dto.TicketUpdateNumDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TicketMapper {
    void save(Ticket ticket);
    Optional<Ticket> findById(Long id);
    Optional<Ticket> findByTotalNum(Integer totalNum);
    List<Ticket> findAll();
    void updateNum(Long id, TicketUpdateNumDto ticketUpdateNumDto);
}
