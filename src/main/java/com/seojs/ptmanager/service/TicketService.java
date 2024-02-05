package com.seojs.ptmanager.service;

import com.seojs.ptmanager.domain.member.Member;
import com.seojs.ptmanager.domain.member.MemberRepository;
import com.seojs.ptmanager.domain.memberticket.MemberTicket;
import com.seojs.ptmanager.domain.memberticket.MemberTicketRepository;
import com.seojs.ptmanager.domain.ticket.Ticket;
import com.seojs.ptmanager.domain.ticket.TicketRepository;
import com.seojs.ptmanager.exception.MemberNotFoundEx;
import com.seojs.ptmanager.exception.TicketDuplicateEx;
import com.seojs.ptmanager.exception.TicketNotFoundEx;
import com.seojs.ptmanager.web.dto.MemberTicketDto;
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
    private final MemberRepository memberRepository;
    private final TicketRepository ticketRepository;
    private final MemberTicketRepository memberTicketRepository;

    @Transactional
    public Long save(TicketDto ticketDto) {
        ticketDuplicateCheck(ticketDto.getTotalNum());

        return ticketRepository.save(new Ticket(ticketDto.getTotalNum(), ticketDto.getPrice()));
    }

    @Transactional
    public TicketResponseDto findById(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundEx("이용권이 없습니다. id = " + id));

        return new TicketResponseDto(ticket);
    }

    @Transactional
    public List<TicketResponseDto> findAll() {
        return ticketRepository.findAll().stream()
                .map(TicketResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void buyTicket(MemberTicketDto memberTicketDto) {
        Member member = memberRepository.findById(memberTicketDto.getMemberId()).orElseThrow(() -> new MemberNotFoundEx("멤버가 없습니다. id = " + memberTicketDto.getMemberId()));
        Ticket ticket = ticketRepository.findById(memberTicketDto.getTicketId()).orElseThrow(() -> new TicketNotFoundEx("이용권이 없습니다. id = " + memberTicketDto.getTicketId()));

        MemberTicket memberTicket = new MemberTicket(memberTicketDto.getMemberId(), memberTicketDto.getTicketId());
        memberTicketRepository.save(memberTicket);

        member.addTicket(ticket);
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
