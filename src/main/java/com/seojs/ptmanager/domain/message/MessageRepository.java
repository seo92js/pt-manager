package com.seojs.ptmanager.domain.message;

import com.seojs.ptmanager.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final MessageMapper messageMapper;

    public Long save(Message message) {
        messageMapper.save(message);
        return message.getId();
    }

    public List<Message> findByMemberIdAndTrainerId(Long memberId, Long trainerId) {
        return messageMapper.findByMemberIdAndTrainerId(memberId, trainerId);
    }

    public List<Message> findBySendMemberId(Long memberId) {
        return messageMapper.findBySendMemberId(memberId);
    }

    public List<Message> findByReceiveMemberId(Long memberId) {
        return messageMapper.findByReceiveMemberId(memberId);
    }

    public List<Message> findBySendTrainerId(Long trainerId) {
        return messageMapper.findBySendTrainerId(trainerId);
    }

    public List<Message> findByReceiveTrainerId(Long trainerId) {
        return messageMapper.findByReceiveTrainerId(trainerId);
    }
}
