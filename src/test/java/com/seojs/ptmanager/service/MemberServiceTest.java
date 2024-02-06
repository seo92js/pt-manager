package com.seojs.ptmanager.service;

import com.seojs.ptmanager.exception.MemberDuplicateEx;
import com.seojs.ptmanager.exception.MemberNotFoundEx;
import com.seojs.ptmanager.web.dto.MemberDto;
import com.seojs.ptmanager.web.dto.MemberResponseDto;
import com.seojs.ptmanager.web.dto.MessageDto;
import com.seojs.ptmanager.web.dto.TrainerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

        List<MemberResponseDto> all1 = memberService.findAll("");
        assertThat(all1.size()).isEqualTo(3);

        List<MemberResponseDto> all2 = memberService.findAll(null);
        assertThat(all2.size()).isEqualTo(3);

        List<MemberResponseDto> find1 = memberService.findAll("김");
        assertThat(find1.size()).isEqualTo(2);

        List<MemberResponseDto> find2 = memberService.findAll("구라");
        assertThat(find2.size()).isEqualTo(1);

        List<MemberResponseDto> find3 = memberService.findAll("윤종신");
        assertThat(find3.size()).isEqualTo(1);
    }

    @Test
    void MemberNotFoundEx_테스트() {
        assertThatThrownBy(() -> memberService.findById(1L))
                .isInstanceOf(MemberNotFoundEx.class);
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
}