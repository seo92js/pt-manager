package com.seojs.ptmanager.service;

import com.seojs.ptmanager.domain.member.Member;
import com.seojs.ptmanager.domain.member.MemberRepository;
import com.seojs.ptmanager.domain.reserve.Reserve;
import com.seojs.ptmanager.domain.reserve.ReserveRepository;
import com.seojs.ptmanager.domain.ticket.Ticket;
import com.seojs.ptmanager.domain.ticket.TicketRepository;
import com.seojs.ptmanager.domain.trainer.Trainer;
import com.seojs.ptmanager.domain.trainer.TrainerRepository;
import com.seojs.ptmanager.exception.MemberNotFoundEx;
import com.seojs.ptmanager.exception.TicketNotFoundEx;
import com.seojs.ptmanager.exception.TrainerNotFoundEx;
import com.seojs.ptmanager.web.dto.ReserveDto;
import com.seojs.ptmanager.web.dto.TicketUpdateNumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;
    private final ReserveRepository reserveRepository;

    @Transactional
    public void reserve(ReserveDto reserveDto) {
        Member member = memberRepository.findById(reserveDto.getMemberId()).orElseThrow(() -> new MemberNotFoundEx("멤버가 없습니다. id = " + reserveDto.getMemberId()));
        Trainer trainer = trainerRepository.findById(reserveDto.getTrainerId()).orElseThrow(() -> new TrainerNotFoundEx("트레이너가 없습니다. id + " + reserveDto.getTrainerId()));
        Ticket ticket = ticketRepository.findById(reserveDto.getTicketId()).orElseThrow(() -> new TicketNotFoundEx("이용권이 없습니다. id + " + reserveDto.getTicketId()));

        ticket.use();

        Reserve reserve = new Reserve(member, trainer, ticket, reserveDto.getReserveTime());

        member.addReserve(reserve);

        reserveRepository.save(reserve);

        ticketService.updateRemainNum(reserveDto.getTicketId(), new TicketUpdateNumDto(ticket.getRemainNum(), ticket.getStatus()));
    }
}
