package com.seojs.ptmanager.domain.admin;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminMapper {
    void save(Admin admin);

    Optional<Admin> findById(Long id);

    List<Admin> findAll();
}
