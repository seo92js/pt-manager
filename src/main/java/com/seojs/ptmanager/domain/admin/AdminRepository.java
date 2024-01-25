package com.seojs.ptmanager.domain.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdminRepository {
    private final AdminMapper adminMapper;

    public Admin save(Admin admin) {
        adminMapper.save(admin);
        return admin;
    }

    public Optional<Admin> findById(Long id) {
        return adminMapper.findById(id);
    }

    public List<Admin> findAll() {
        return adminMapper.findAll();
    }
}
