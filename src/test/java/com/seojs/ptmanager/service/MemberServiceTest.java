package com.seojs.ptmanager.service;

import com.seojs.ptmanager.exception.MemberDuplicateEx;
import com.seojs.ptmanager.web.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    TrainerService trainerService;

    @Autowired
    MessageService messageService;

    @Autowired
    TicketService ticketService;

    @Test
    void save() {
        MemberDto memberDto = new MemberDto("id","name","pw");

        Long savedId = memberService.save(memberDto);

        MemberResponseDto memberResponseDto = memberService.findById(savedId);

        assertThat(memberResponseDto.getLoginId()).isEqualTo(memberDto.getLoginId());
        assertThat(memberResponseDto.getName()).isEqualTo(memberDto.getName());
    }

    @Test
    void findAll() {
        MemberDto memberDto1 = new MemberDto("id1","김구라","pw1");
        MemberDto memberDto2 = new MemberDto("id2","김국진","pw2");
        MemberDto memberDto3 = new MemberDto("id3","윤종신","pw3");

        memberService.save(memberDto1);
        memberService.save(memberDto2);
        memberService.save(memberDto3);

        List<MemberResponseDto> all = memberService.findAll(null);
        assertThat(all.size()).isEqualTo(3);

        List<MemberResponseDto> find1 = memberService.findAll("김");
        assertThat(find1.size()).isEqualTo(2);

        List<MemberResponseDto> find2 = memberService.findAll("구라");
        assertThat(find2.size()).isEqualTo(1);

        List<MemberResponseDto> find3 = memberService.findAll("윤종신");
        assertThat(find3.size()).isEqualTo(1);
    }

    @Test
    void 로그인_아이디_중복검사() {
        MemberDto memberDto1 = new MemberDto("id","김구라","pw1");

        memberService.save(memberDto1);

        MemberDto memberDto2 = new MemberDto("id","김국진","pw2");

        assertThatThrownBy(() -> memberService.save(memberDto2))
                .isInstanceOf(MemberDuplicateEx.class);
    }

    @Test
    void 멤버와_메시지_함께찾기() {
        MemberDto memberDto = new MemberDto("id","멤버","pw");
        Long memberId = memberService.save(memberDto);

        TrainerDto trainerDto = new TrainerDto("id", "트레이너", "pw");
        Long trainerId = trainerService.save(trainerDto);

        MessageDto messageDto1 = new MessageDto("member -> trainer", memberId, trainerId, null, null);
        messageService.save(messageDto1);

        MessageDto messageDto2 = new MessageDto("trainer -> member", null, null, trainerId, memberId);
        messageService.save(messageDto2);

        MemberResponseDto findMember = memberService.findById(memberId);
        assertThat(findMember.getSentMessages().size()).isEqualTo(1);
        assertThat(findMember.getReceivedMessages().size()).isEqualTo(1);
        assertThat(findMember.getSentMessages().get(0).getContent()).isEqualTo(messageDto1.getContent());
        assertThat(findMember.getReceivedMessages().get(0).getContent()).isEqualTo(messageDto2.getContent());
    }

    @Test
    void 이용권_구매() {
        MemberDto memberDto = new MemberDto("id","name","pw");
        Long memberId = memberService.save(memberDto);

        MemberDto memberDto2 = new MemberDto("id2","name","pw");
        Long memberId2 = memberService.save(memberDto2);

        TicketDto ticketDto1 = new TicketDto(5, 30000);
        Long ticketId1 = ticketService.save(ticketDto1);

        TicketDto ticketDto2 = new TicketDto(10, 50000);
        Long ticketId2 = ticketService.save(ticketDto2);

        //티켓 1 구매
        MemberTicketDto memberTicketDto1 = new MemberTicketDto(memberId, ticketId1);
        memberService.buyTicket(memberTicketDto1);

        assertThat(memberService.findById(memberId).getTickets().size()).isEqualTo(1);
        assertThat(memberService.findById(memberId).getTickets().get(0).getTotalNum()).isEqualTo(ticketDto1.getTotalNum());
        assertThat(memberService.findById(memberId).getTickets().get(0).getRemainNum()).isEqualTo(ticketDto1.getTotalNum());
        assertThat(memberService.findById(memberId).getTickets().get(0).getPrice()).isEqualTo(ticketDto1.getPrice());
        assertThat(memberService.findById(memberId).getTickets().get(0).getStatus()).isEqualTo(true);

        //티켓 2 구매
        MemberTicketDto memberTicketDto2 = new MemberTicketDto(memberId, ticketId2);
        memberService.buyTicket(memberTicketDto2);

        assertThat(memberService.findById(memberId).getTickets().size()).isEqualTo(2);
        assertThat(memberService.findById(memberId).getTickets().get(1).getTotalNum()).isEqualTo(ticketDto2.getTotalNum());
        assertThat(memberService.findById(memberId).getTickets().get(1).getPrice()).isEqualTo(ticketDto2.getPrice());

        //멤버 2 는 티켓 0장
        assertThat(memberService.findById(memberId2).getTickets().size()).isEqualTo(0);

    }

    @Test
    void 예약_후_조회() {
        MemberDto memberDto = new MemberDto("id","name","pw");
        Long memberId = memberService.save(memberDto);

        TrainerDto trainerDto = new TrainerDto("id", "트레이너", "pw");
        Long trainerId = trainerService.save(trainerDto);

        TicketDto ticketDto = new TicketDto(5, 30000);
        Long ticketId = ticketService.save(ticketDto);

        //티켓 구매
        MemberTicketDto memberTicketDto = new MemberTicketDto(memberId, ticketId);
        memberService.buyTicket(memberTicketDto);

        //예약
        LocalDateTime now = LocalDateTime.now();
        ReserveDto reserveDto = new ReserveDto(memberId, trainerId, ticketId, now);
        memberService.reserve(reserveDto);

        MemberResponseDto member = memberService.findById(memberId);
        assertThat(member.getReserves().get(0).getReserveTime()).isEqualTo(now);
        assertThat(member.getTickets().get(0).getRemainNum()).isEqualTo(4);
        assertThat(member.getTickets().get(0).getStatus()).isEqualTo(true);

        memberService.reserve(reserveDto);
        memberService.reserve(reserveDto);
        memberService.reserve(reserveDto);
        memberService.reserve(reserveDto);

        member = memberService.findById(memberId);
        assertThat(member.getTickets().get(0).getRemainNum()).isEqualTo(0);
        assertThat(member.getTickets().get(0).getStatus()).isEqualTo(false);
    }
}