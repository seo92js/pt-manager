package com.seojs.ptmanager.service;

import com.seojs.ptmanager.domain.member.Member;
import com.seojs.ptmanager.domain.member.MemberRepository;
import com.seojs.ptmanager.domain.message.Message;
import com.seojs.ptmanager.domain.message.MessageRepository;
import com.seojs.ptmanager.domain.trainer.Trainer;
import com.seojs.ptmanager.domain.trainer.TrainerRepository;
import com.seojs.ptmanager.exception.MemberNotFoundEx;
import com.seojs.ptmanager.exception.TrainerNotFoundEx;
import com.seojs.ptmanager.web.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public Long save(MessageDto messageDto) {
        if (messageDto.getSendMemberId() != null) {
            Member sendMember = memberRepository.findById(messageDto.getSendMemberId()).orElseThrow(() -> new MemberNotFoundEx("멤버가 없습니다. id = " + messageDto.getSendMemberId()));
            Trainer receiveTrainer = trainerRepository.findById(messageDto.getReceiveTrainerId()).orElseThrow(() -> new TrainerNotFoundEx("트레이너가 없습니다. Id = " + messageDto.getReceiveTrainerId()));

            Message message = new Message(messageDto.getContent(), sendMember, receiveTrainer);

            sendMember.addSentMessage(message);
            receiveTrainer.addReceivedMessage(message);

            return messageRepository.save(message);

        } else if (messageDto.getSendTrainerId() != null) {
            Trainer sendTrainer = trainerRepository.findById(messageDto.getSendTrainerId()).orElseThrow(() -> new TrainerNotFoundEx("트레이너가 없습니다. id = " + messageDto.getSendTrainerId()));
            Member receiveMember = memberRepository.findById(messageDto.getReceiveMemberId()).orElseThrow(() -> new MemberNotFoundEx("멤버가 없습니다. id = " + messageDto.getReceiveMemberId()));

            Message message = new Message(messageDto.getContent(), sendTrainer, receiveMember);

            sendTrainer.addSentMessage(message);
            receiveMember.addReceivedMessage(message);

            return messageRepository.save(message);
        }

        return null;
    }

    @Transactional
    public List<Message> findByMemberIdAndTrainerId(Long memberId, Long trainerId) {
        return messageRepository.findByMemberIdAndTrainerId(memberId, trainerId);
    }

    @Transactional
    public List<Message> findBySendMemberId(Long memberId) {
        return messageRepository.findBySendMemberId(memberId);
    }

    @Transactional
    public List<Message> findBySendTrainerId(Long trainerId) {
        return messageRepository.findBySendTrainerId(trainerId);
    }

    @Transactional
    public List<Message> findByReceiveTrainerId(Long trainerId) {
        return messageRepository.findByReceiveTrainerId(trainerId);
    }

    @Transactional
    public List<Message> findByReceiveMemberId(Long memberId) {
        return messageRepository.findByReceiveMemberId(memberId);
    }
}
