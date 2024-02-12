package com.seojs.ptmanager.web;

import com.seojs.ptmanager.domain.reserve.Reserve;
import com.seojs.ptmanager.service.ReserveService;
import com.seojs.ptmanager.web.dto.ReserveDto;
import com.seojs.ptmanager.web.dto.ReserveDateFindDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReserveApiController {
    private final ReserveService reserveService;

    @PatchMapping("/api/v1/reserve")
    public void reserve(@RequestBody ReserveDto reserveDto) {
        reserveService.reserve(reserveDto);
    }

    @GetMapping("/api/v1/reserve/member")
    public List<Reserve> findByMemberIdAndDate(@RequestBody ReserveDateFindDto reserveDateFindDto) {
        return reserveService.findByMemberIdAndDate(reserveDateFindDto);
    }
}
