package com.seojs.ptmanager.web;

import com.seojs.ptmanager.service.ReserveService;
import com.seojs.ptmanager.web.dto.ReserveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReserveApiController {
    private final ReserveService reserveService;

    @PatchMapping("/api/v1/reserve")
    public void reserve(@RequestBody ReserveDto reserveDto) {
        reserveService.reserve(reserveDto);
    }
}
