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
import com.seojs.ptmanager.exception.ReserveDuplicateEx;
import com.seojs.ptmanager.exception.TicketNotFoundEx;
import com.seojs.ptmanager.exception.TrainerNotFoundEx;
import com.seojs.ptmanager.web.dto.ReserveDateFindDto;
import com.seojs.ptmanager.web.dto.ReserveDateTimeFindDto;
import com.seojs.ptmanager.web.dto.ReserveDto;
import com.seojs.ptmanager.web.dto.TicketUpdateNumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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

        Reserve reserve = new Reserve(member, trainer, ticket, reserveDto.getReserveTime().truncatedTo(ChronoUnit.HOURS));

        reserveDuplicateCheck(reserveDto.getMemberId(), reserve.getReserveTime());

        member.addReserve(reserve);

        reserveRepository.save(reserve);

        ticketService.updateRemainNum(reserveDto.getTicketId(), new TicketUpdateNumDto(ticket.getRemainNum(), ticket.getStatus()));
    }

    @Transactional
    public List<Reserve> findByMemberIdAndDate(ReserveDateFindDto reserveDateFindDto) {
        return reserveRepository.findByMemberIdAndDate(reserveDateFindDto);
    }

    @Transactional
    public Optional<Reserve> findByMemberIdAndDateTime(ReserveDateTimeFindDto reserveDateTimeFindDto) {
        return reserveRepository.findByMemberIdAndDateTime(reserveDateTimeFindDto);
    }

    void reserveDuplicateCheck(Long memberId, LocalDateTime localDateTime) {
        ReserveDateTimeFindDto reserveDateTimeFindDto = new ReserveDateTimeFindDto(memberId, localDateTime);

        reserveRepository.findByMemberIdAndDateTime(reserveDateTimeFindDto).ifPresent(existingReserve -> {
            throw new ReserveDuplicateEx("중복 된 예약이 있습니다. reserveTime = " + localDateTime);
        });
    }
}
