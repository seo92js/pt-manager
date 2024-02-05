package com.seojs.ptmanager.service;

import com.seojs.ptmanager.exception.TrainerDuplicateEx;
import com.seojs.ptmanager.web.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class TrainerServiceTest {
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
        TrainerDto trainerDto = new TrainerDto("id","username","pw");

        Long savedId = trainerService.save(trainerDto);

        TrainerResponseDto trainerResponseDto = trainerService.findById(savedId);

        assertThat(trainerResponseDto.getLoginId()).isEqualTo(trainerDto.getLoginId());
        assertThat(trainerResponseDto.getName()).isEqualTo(trainerDto.getName());
        assertThat(trainerResponseDto.getStartTime()).isEqualTo(LocalTime.of(0,0, 0));
        assertThat(trainerResponseDto.getEndTime()).isEqualTo(LocalTime.of(23,59, 59));
    }

    @Test
    void findAll() {
        TrainerDto trainerDto1 = new TrainerDto("id1","유세윤","pw1");
        TrainerDto trainerDto2 = new TrainerDto("id2","안영미","pw2");
        TrainerDto trainerDto3 = new TrainerDto("id3","유상무","pw3");

        trainerService.save(trainerDto1);
        trainerService.save(trainerDto2);
        trainerService.save(trainerDto3);

        List<TrainerResponseDto> all = trainerService.findAll(null);
        assertThat(all.size()).isEqualTo(3);

        List<TrainerResponseDto> find1 = trainerService.findAll("유");
        assertThat(find1.size()).isEqualTo(2);

        List<TrainerResponseDto> find2 = trainerService.findAll("영미");
        assertThat(find2.size()).isEqualTo(1);

        List<TrainerResponseDto> find3 = trainerService.findAll("유상무");
        assertThat(find3.size()).isEqualTo(1);
    }

    @Test
    void 로그인_아이디_중복검사() {
        TrainerDto trainerDto1 = new TrainerDto("id","김구라","pw1");

        trainerService.save(trainerDto1);

        TrainerDto trainerDto2 = new TrainerDto("id","김국진","pw2");

        assertThatThrownBy(() -> trainerService.save(trainerDto2))
                .isInstanceOf(TrainerDuplicateEx.class);
    }

    @Test
    void 근무시간_변경() {
        TrainerDto trainerDto = new TrainerDto("id","트레이너","비밀번호");
        trainerService.save(trainerDto);

        TrainerResponseDto findTrainer = trainerService.findByLoginId(trainerDto.getLoginId());

        assertThat(findTrainer.getStartTime()).isEqualTo(LocalTime.of(0,0,0));
        assertThat(findTrainer.getEndTime()).isEqualTo(LocalTime.of(23,59,59));

        LocalTime expectedStartTime = LocalTime.of(9,0,0);
        LocalTime expectedEndTime = LocalTime.of(18,30,0);

        TrainerTimeUpdateDto trainerTimeUpdateDto = new TrainerTimeUpdateDto(expectedStartTime, expectedEndTime);

        trainerService.update(findTrainer.getId(), trainerTimeUpdateDto);

        TrainerResponseDto updatedTrainer = trainerService.findByLoginId(trainerDto.getLoginId());

        assertThat(updatedTrainer.getStartTime()).isEqualTo(expectedStartTime);
        assertThat(updatedTrainer.getEndTime()).isEqualTo(expectedEndTime);
    }

    @Test
    void 트레이너와_메시지_함게찾기() {
        MemberDto memberDto = new MemberDto("id","멤버","pw");
        Long memberId = memberService.save(memberDto);

        TrainerDto trainerDto = new TrainerDto("id", "트레이너", "pw");
        Long trainerId = trainerService.save(trainerDto);

        MessageDto messageDto1 = new MessageDto("member -> trainer", memberId, trainerId, null, null);
        messageService.save(messageDto1);

        MessageDto messageDto2 = new MessageDto("trainer -> member", null, null, trainerId, memberId);
        messageService.save(messageDto2);

        TrainerResponseDto findTrainer = trainerService.findById(trainerId);
        assertThat(findTrainer.getSentMessages().size()).isEqualTo(1);
        assertThat(findTrainer.getReceivedMessages().size()).isEqualTo(1);
        assertThat(findTrainer.getSentMessages().get(0).getContent()).isEqualTo(messageDto2.getContent());
        assertThat(findTrainer.getReceivedMessages().get(0).getContent()).isEqualTo(messageDto1.getContent());
    }
}