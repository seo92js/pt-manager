package com.seojs.ptmanager.service;

import com.seojs.ptmanager.domain.admin.Admin;
import com.seojs.ptmanager.domain.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    @Transactional
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Transactional
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    @Transactional
    public List<Admin> findAll(){
        return adminRepository.findAll();
    }
}
