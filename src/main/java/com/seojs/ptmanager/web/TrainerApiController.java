package com.seojs.ptmanager.web;

import com.seojs.ptmanager.service.TrainerService;
import com.seojs.ptmanager.web.dto.TrainerDto;
import com.seojs.ptmanager.web.dto.TrainerResponseDto;
import com.seojs.ptmanager.web.dto.TrainerTimeUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainerApiController {
    private final TrainerService trainerService;

    @PostMapping("/api/v1/trainer")
    public Long save(@RequestBody TrainerDto trainerDto) {
        return trainerService.save(trainerDto);
    }

    @GetMapping("/api/v1/trainer/{id}")
    public TrainerResponseDto findById(@PathVariable Long id) {
        return trainerService.findById(id);
    }

    @GetMapping("/api/v1/trainer/login-id/{loginId}")
    public TrainerResponseDto findByLoginId(@PathVariable String loginId) {
        return trainerService.findByLoginId(loginId);
    }

    @GetMapping({"/api/v1/trainers/{name}", "/api/v1/trainers"})
    public List<TrainerResponseDto> findAll(@PathVariable(required = false) String name) {
        return trainerService.findAll(name);
    }

    @PatchMapping("/api/v1/trainer/{id}/time")
    public void updateTime(@PathVariable Long id, @RequestBody TrainerTimeUpdateDto trainerTimeUpdateDto) {
        trainerService.update(id, trainerTimeUpdateDto);
    }
}
