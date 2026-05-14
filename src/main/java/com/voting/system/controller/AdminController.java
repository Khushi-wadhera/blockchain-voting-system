package com.voting.system.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voting.system.entity.Admin;
import com.voting.system.service.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Register Admin
    @PostMapping("/register")
    public Admin registerAdmin(
            @RequestBody Admin admin) {

        return adminService.createAdmin(admin);
    }

    // Admin Login
    @PostMapping("/login")
    public Map<String, Object> loginAdmin(
            @RequestBody Admin admin) {

        Optional<Admin> loggedAdmin =
                adminService.login(
                        admin.getUsername(),
                        admin.getPassword());

        Map<String, Object> response =
                new HashMap<>();

        if (loggedAdmin.isPresent()) {

            response.put("success", true);
            response.put("message", "Admin login successful");

        } else {

            response.put("success", false);
            response.put("message", "Invalid username or password");
        }

        return response;
    }
}