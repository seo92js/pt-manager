package com.seojs.ptmanager.mapper;

import com.seojs.ptmanager.domain.trainer.Trainer;
import com.seojs.ptmanager.web.dto.TrainerTimeUpdateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TrainerMapper {
    void save(Trainer trainer);
    Optional<Trainer> findById(Long id);
    Optional<Trainer> findByLoginId(String loginId);
    List<Trainer> findAll(String name);

    void update(Long id, TrainerTimeUpdateDto trainerTimeUpdateDto);
}
