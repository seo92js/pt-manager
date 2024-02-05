package com.seojs.ptmanager.web;

import com.seojs.ptmanager.domain.message.Message;
import com.seojs.ptmanager.service.MessageService;
import com.seojs.ptmanager.web.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MessageApiController {
    private final MessageService messageService;

    @PostMapping("/api/v1/message")
    public Long save(@RequestBody MessageDto messageDto) {
        return messageService.save(messageDto);
    }

    @GetMapping("/api/v1/message/{memberId}/{trainerId}")
    public List<Message> findByMemberIdAndTrainerId(@PathVariable Long memberId, @PathVariable Long trainerId) {
        return messageService.findByMemberIdAndTrainerId(memberId, trainerId);
    }

    @GetMapping("/api/v1/message/send-member/{memberId}")
    public List<Message> findBySendMemberId(@PathVariable Long memberId) {
        return messageService.findBySendMemberId(memberId);
    }

    @GetMapping("/api/v1/message/receive-member/{memberId}")
    public List<Message> findByReceiveMemberId(@PathVariable Long memberId) {
        return messageService.findByReceiveMemberId(memberId);
    }

    @GetMapping("/api/v1/message/send-trainer/{memberId}")
    public List<Message> findBySendTrainerId(@PathVariable Long memberId) {
        return messageService.findBySendTrainerId(memberId);
    }

    @GetMapping("/api/v1/message/receive-trainer/{memberId}")
    public List<Message> findByReceiveTrainerId(@PathVariable Long memberId) {
        return messageService.findByReceiveTrainerId(memberId);
    }
}
