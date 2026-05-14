package com.voting.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.voting.system.entity.Admin;
import com.voting.system.repository.AdminRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {

        if (adminRepository.findByUsername("admin").isEmpty()) {

            Admin admin = new Admin();

            admin.setUsername("admin");

            admin.setPassword(
                    passwordEncoder.encode("admin123"));

            adminRepository.save(admin);

            System.out.println("Default Admin Created");
        }
    }
}