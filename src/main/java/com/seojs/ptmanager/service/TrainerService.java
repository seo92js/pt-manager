package com.seojs.ptmanager.service;

import com.seojs.ptmanager.domain.message.Message;
import com.seojs.ptmanager.domain.message.MessageRepository;
import com.seojs.ptmanager.domain.reserve.Reserve;
import com.seojs.ptmanager.domain.reserve.ReserveRepository;
import com.seojs.ptmanager.domain.trainer.Trainer;
import com.seojs.ptmanager.domain.trainer.TrainerRepository;
import com.seojs.ptmanager.exception.TrainerDuplicateEx;
import com.seojs.ptmanager.exception.TrainerNotFoundEx;
import com.seojs.ptmanager.web.dto.TrainerDto;
import com.seojs.ptmanager.web.dto.TrainerResponseDto;
import com.seojs.ptmanager.web.dto.TrainerTimeUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final MessageRepository messageRepository;
    private final ReserveRepository reserveRepository;

    @Transactional
    public Long save(TrainerDto trainerDto) {
        trainerDuplicateCheck(trainerDto.getLoginId());

        return trainerRepository.save(new Trainer(trainerDto.getLoginId(), trainerDto.getName(), trainerDto.getPassword()));
    }

    @Transactional
    public TrainerResponseDto findById(Long id) {
        Trainer trainer = trainerRepository.findById(id).orElseThrow(() -> new TrainerNotFoundEx("트레이너가 없습니다. id = " + id));

        //보낸메시지
        List<Message> sentMessages = messageRepository.findBySendTrainerId(id);
        trainer.addAllSentMessage(sentMessages);

        //받은메시지
        List<Message> receivedMessages = messageRepository.findByReceiveTrainerId(id);
        trainer.addAllReceivedMessage(receivedMessages);

        //예약
        List<Reserve> reserves = reserveRepository.findByTrainerId(id);
        trainer.addAllReserve(reserves);

        return new TrainerResponseDto(trainer);
    }

    @Transactional
    public TrainerResponseDto findByLoginId(String loginId) {
        Trainer trainer = trainerRepository.findByLoginId(loginId).orElseThrow(() -> new TrainerNotFoundEx("트레이너가 없습니다. loginId = " + loginId));

        List<Message> sentMessages = messageRepository.findBySendTrainerId(trainer.getId());
        trainer.addAllSentMessage(sentMessages);

        List<Message> receivedMessages = messageRepository.findByReceiveTrainerId(trainer.getId());
        trainer.addAllReceivedMessage(receivedMessages);

        return new TrainerResponseDto(trainer);
    }

    @Transactional
    public List<TrainerResponseDto> findAll(String name) {
        return trainerRepository.findAll(name).stream()
                .map(TrainerResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long id, TrainerTimeUpdateDto trainerTimeUpdateDto) {
        trainerRepository.update(id, trainerTimeUpdateDto);
    }

    void trainerDuplicateCheck(String loginId) {
        trainerRepository.findByLoginId(loginId).ifPresent(existingTrainer -> {
            throw new TrainerDuplicateEx("중복 된 트레이너가 있습니다. loginId=" + loginId);
        });
    }
}
