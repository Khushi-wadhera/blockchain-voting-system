package com.voting.system.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.voting.system.entity.Admin;
import com.voting.system.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public Admin createAdmin(Admin admin) {

        admin.setPassword(
                passwordEncoder.encode(admin.getPassword()));

        return adminRepository.save(admin);
    }

    public Optional<Admin> login(
            String username,
            String password) {

        Optional<Admin> admin =
                adminRepository.findByUsername(username);

        if (admin.isPresent()) {

            boolean match =
                    passwordEncoder.matches(
                            password,
                            admin.get().getPassword());

            if (match) {
                return admin;
            }
        }

        return Optional.empty();
    }
}