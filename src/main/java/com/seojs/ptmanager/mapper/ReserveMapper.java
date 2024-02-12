package com.seojs.ptmanager.mapper;

import com.seojs.ptmanager.domain.reserve.Reserve;
import com.seojs.ptmanager.web.dto.ReserveDateFindDto;
import com.seojs.ptmanager.web.dto.ReserveDateTimeFindDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReserveMapper {
    void save(Reserve reserve);
    List<Reserve> findByMemberId(Long memberId);
    List<Reserve> findByTrainerId(Long trainerId);
    List<Reserve> findByMemberIdAndDate(ReserveDateFindDto reserveDateFindDto);
    Optional<Reserve> findByMemberIdAndDateTime(ReserveDateTimeFindDto reserveDateTimeFindDto);
}
