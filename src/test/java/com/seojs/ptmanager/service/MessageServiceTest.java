package com.seojs.ptmanager.service;

import com.seojs.ptmanager.domain.message.Message;
import com.seojs.ptmanager.web.dto.MemberDto;
import com.seojs.ptmanager.web.dto.MessageDto;
import com.seojs.ptmanager.web.dto.TrainerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MessageServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    TrainerService trainerService;

    @Autowired
    MessageService messageService;

    @Test
    void save() {
        Long memberId = memberService.save(new MemberDto("member1","member1","pw"));

        Long trainerId = trainerService.save(new TrainerDto("trainer1","trainer1","pw"));

        MessageDto member1MessageDto = new MessageDto("member1 -> trainer1", memberId, trainerId, null, null);

        messageService.save(member1MessageDto);

        List<Message> byMemberIdAndTrainerId = messageService.findByMemberIdAndTrainerId(memberId, trainerId);
        Message message = messageService.findByMemberIdAndTrainerId(memberId, trainerId).get(0);

        assertThat(message.getContent()).isEqualTo("member1 -> trainer1");
        assertThat(message.getIsRead()).isEqualTo(false);
        assertThat(message.getSendMember().getId()).isEqualTo(memberId);
        assertThat(message.getReceiveTrainer().getId()).isEqualTo(trainerId);

        LocalDateTime nowDateTime = LocalDateTime.now();
        assertThat(message.getCreatedDate()).isBefore(nowDateTime);
    }

    @Test
    void 멤버ID와_트레이너ID로_메시지찾기() {
        Long member1Id = memberService.save(new MemberDto("member1","member1","pw"));
        Long member2Id = memberService.save(new MemberDto("member2","member2","pw"));
        Long trainer1Id = trainerService.save(new TrainerDto("trainer1","trainer1","pw"));
        Long trainer2Id = trainerService.save(new TrainerDto("trainer2","trainer2","pw"));

        // member 1 -> trainer 1, trainer 1 -> member 1
        messageService.save(new MessageDto("member1 -> trainer1", member1Id, trainer1Id, null, null));
        messageService.save(new MessageDto("trainer1 -> member1", null, null, trainer1Id, member1Id));

        List<Message> all = messageService.findByMemberIdAndTrainerId(member1Id, trainer1Id);
        assertThat(all.size()).isEqualTo(2);

        // trainer 1 -> member 2
        messageService.save(new MessageDto("trainer1 -> member2", null, null, trainer1Id, member2Id));

        List<Message> all2 = messageService.findByMemberIdAndTrainerId(member2Id, trainer1Id);
        Message findMessage = all2.get(0);

        assertThat(all2.size()).isEqualTo(1);
        assertThat(findMessage.getSendTrainer().getId()).isEqualTo(trainer1Id);
        assertThat(findMessage.getReceiveMember().getId()).isEqualTo(member2Id);

        // trainer 2 -> member 1
        messageService.save(new MessageDto("trainer2 -> member1", null, null, trainer2Id, member1Id));

        List<Message> all3 = messageService.findByMemberIdAndTrainerId(member1Id, trainer2Id);
        assertThat(all3.size()).isEqualTo(1);
        assertThat(all3.get(0).getSendTrainer().getId()).isEqualTo(trainer2Id);
        assertThat(all3.get(0).getReceiveMember().getId()).isEqualTo(member1Id);
    }

    @Test
    void 멤버ID로_보낸메시지_찾기() {
        Long member1Id = memberService.save(new MemberDto("member1","member1","pw"));
        Long member2Id = memberService.save(new MemberDto("member2","member2","pw"));
        Long trainer1Id = trainerService.save(new TrainerDto("trainer1","trainer1","pw"));
        Long trainer2Id = trainerService.save(new TrainerDto("trainer2","trainer2","pw"));

        // member 1 -> trainer 1, member 1 -> trainer 2
        messageService.save(new MessageDto("member1 -> trainer1", member1Id, trainer1Id, null, null));
        messageService.save(new MessageDto("member1 -> trainer2", member1Id, trainer2Id, null, null));

        // member 2 -> trainer 1, member 1 -> trainer 2
        messageService.save(new MessageDto("member2 -> trainer1", member2Id, trainer1Id, null, null));
        messageService.save(new MessageDto("member2 -> trainer2", member2Id, trainer2Id, null, null));

        List<Message> member1SendMessages = messageService.findBySendMemberId(member1Id);

        assertThat(member1SendMessages.size()).isEqualTo(2);
        assertThat(member1SendMessages.get(0).getContent()).isEqualTo("member1 -> trainer1");
        assertThat(member1SendMessages.get(1).getContent()).isEqualTo("member1 -> trainer2");

        List<Message> member2SendMessages = messageService.findBySendMemberId(member2Id);

        assertThat(member2SendMessages.size()).isEqualTo(2);
        assertThat(member2SendMessages.get(0).getContent()).isEqualTo("member2 -> trainer1");
        assertThat(member2SendMessages.get(1).getContent()).isEqualTo("member2 -> trainer2");
    }

    @Test
    void 멤버ID로_받은메시지_찾기() {
        Long member1Id = memberService.save(new MemberDto("member1","member1","pw"));
        Long member2Id = memberService.save(new MemberDto("member2","member2","pw"));
        Long trainer1Id = trainerService.save(new TrainerDto("trainer1","trainer1","pw"));
        Long trainer2Id = trainerService.save(new TrainerDto("trainer2","trainer2","pw"));

        // trainer 1 -> member 1, trainer 1 -> member 2
        messageService.save(new MessageDto("trainer1 -> member1", null, null, trainer1Id, member1Id));
        messageService.save(new MessageDto("trainer1 -> member2", null, null, trainer1Id, member2Id));

        // trainer 2 -> member 1, trainer 1 -> member 2
        messageService.save(new MessageDto("trainer2 -> member1", null, null, trainer2Id, member1Id));
        messageService.save(new MessageDto("trainer2 -> member2", null, null, trainer2Id, member2Id));

        List<Message> member1ReceiveMessages = messageService.findByReceiveMemberId(member1Id);

        assertThat(member1ReceiveMessages.size()).isEqualTo(2);
        assertThat(member1ReceiveMessages.get(0).getContent()).isEqualTo("trainer1 -> member1");
        assertThat(member1ReceiveMessages.get(1).getContent()).isEqualTo("trainer2 -> member1");

        List<Message> member2ReceiveMessages = messageService.findByReceiveMemberId(member2Id);

        assertThat(member2ReceiveMessages.size()).isEqualTo(2);
        assertThat(member2ReceiveMessages.get(0).getContent()).isEqualTo("trainer1 -> member2");
        assertThat(member2ReceiveMessages.get(1).getContent()).isEqualTo("trainer2 -> member2");
    }

    @Test
    void 트레이너ID로_보낸메시지_찾기() {
        Long member1Id = memberService.save(new MemberDto("member1","member1","pw"));
        Long member2Id = memberService.save(new MemberDto("member2","member2","pw"));
        Long trainer1Id = trainerService.save(new TrainerDto("trainer1","trainer1","pw"));
        Long trainer2Id = trainerService.save(new TrainerDto("trainer2","trainer2","pw"));

        // trainer 1 -> member 1, trainer 1 -> member 2
        messageService.save(new MessageDto("trainer1 -> member1", null, null, trainer1Id, member1Id));
        messageService.save(new MessageDto("trainer1 -> member2", null, null, trainer1Id, member2Id));

        // trainer 2 -> member 1, trainer 2 -> member 2
        messageService.save(new MessageDto("trainer2 -> member1", null, null, trainer2Id, member1Id));
        messageService.save(new MessageDto("trainer2 -> member2", null, null, trainer2Id, member2Id));

        List<Message> trainer1SendMessages = messageService.findBySendTrainerId(trainer1Id);

        assertThat(trainer1SendMessages.size()).isEqualTo(2);
        assertThat(trainer1SendMessages.get(0).getContent()).isEqualTo("trainer1 -> member1");
        assertThat(trainer1SendMessages.get(1).getContent()).isEqualTo("trainer1 -> member2");

        List<Message> trainer2SendMessages = messageService.findBySendTrainerId(trainer2Id);

        assertThat(trainer2SendMessages.size()).isEqualTo(2);
        assertThat(trainer2SendMessages.get(0).getContent()).isEqualTo("trainer2 -> member1");
        assertThat(trainer2SendMessages.get(1).getContent()).isEqualTo("trainer2 -> member2");
    }

    @Test
    void 트레이너ID로_받은메시지_찾기() {
        Long member1Id = memberService.save(new MemberDto("member1","member1","pw"));
        Long member2Id = memberService.save(new MemberDto("member2","member2","pw"));
        Long trainer1Id = trainerService.save(new TrainerDto("trainer1","trainer1","pw"));
        Long trainer2Id = trainerService.save(new TrainerDto("trainer2","trainer2","pw"));

        // member 1 -> trainer 1, member 1 -> trainer 2
        messageService.save(new MessageDto("member1 -> trainer1", member1Id, trainer1Id, null, null));
        messageService.save(new MessageDto("member1 -> trainer2", member1Id, trainer2Id, null, null));

        // member 2 -> trainer 1, member 1 -> trainer 2
        messageService.save(new MessageDto("member2 -> trainer1", member2Id, trainer1Id, null, null));
        messageService.save(new MessageDto("member2 -> trainer2", member2Id, trainer2Id, null, null));

        List<Message> trainer1ReceiveMessages = messageService.findByReceiveTrainerId(trainer1Id);

        assertThat(trainer1ReceiveMessages.size()).isEqualTo(2);
        assertThat(trainer1ReceiveMessages.get(0).getContent()).isEqualTo("member1 -> trainer1");
        assertThat(trainer1ReceiveMessages.get(1).getContent()).isEqualTo("member2 -> trainer1");

        List<Message> trainer2ReceiveMessages = messageService.findByReceiveTrainerId(trainer2Id);

        assertThat(trainer2ReceiveMessages.size()).isEqualTo(2);
        assertThat(trainer2ReceiveMessages.get(0).getContent()).isEqualTo("member1 -> trainer2");
        assertThat(trainer2ReceiveMessages.get(1).getContent()).isEqualTo("member2 -> trainer2");
    }
}