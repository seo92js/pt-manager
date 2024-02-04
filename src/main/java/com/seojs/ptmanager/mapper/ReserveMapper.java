package com.seojs.ptmanager.mapper;

import com.seojs.ptmanager.domain.reserve.Reserve;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReserveMapper {
    void save(Reserve reserve);
    List<Reserve> findByMemberId(Long memberId);
    List<Reserve> findByTrainerId(Long trainerId);
}
