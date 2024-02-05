package com.seojs.ptmanager.web;

import com.seojs.ptmanager.service.TicketService;
import com.seojs.ptmanager.web.dto.MemberTicketDto;
import com.seojs.ptmanager.web.dto.TicketDto;
import com.seojs.ptmanager.web.dto.TicketResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketApiController {
    private final TicketService ticketService;

    @PostMapping("/api/v1/ticket")
    public Long save(@RequestBody TicketDto ticketDto) {
        return ticketService.save(ticketDto);
    }

    @GetMapping("/api/v1/ticket/{id}")
    public TicketResponseDto findById(@PathVariable Long id) {
        return ticketService.findById(id);
    }

    @GetMapping("/api/v1/tickets")
    public List<TicketResponseDto> findAll() {
        return ticketService.findAll();
    }

    @PatchMapping("/api/v1/ticket/buy")
    public void buyTicket(@RequestBody MemberTicketDto memberTicketDto) {
        ticketService.buyTicket(memberTicketDto);
    }
}
