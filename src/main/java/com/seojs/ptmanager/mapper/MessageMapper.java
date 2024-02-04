package com.seojs.ptmanager.mapper;

import com.seojs.ptmanager.domain.message.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    public void save(Message message);

    public List<Message> findByMemberIdAndTrainerId(Long memberId, Long trainerId);

    public List<Message> findBySendMemberId(Long memberId);
    public List<Message> findByReceiveMemberId(Long memberId);

    public List<Message> findBySendTrainerId(Long trainerId);
    public List<Message> findByReceiveTrainerId(Long trainerId);
}
