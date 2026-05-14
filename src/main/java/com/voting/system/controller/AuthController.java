package com.voting.system.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voting.system.security.JwtUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final JwtUtil jwtUtil = new JwtUtil();

    // 🔐 SIMPLE LOGIN (we will upgrade to DB later)
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        Map<String, Object> response = new HashMap<>();

        // ⚠️ TEMP ADMIN CHECK (REAL SYSTEM WILL USE DB LATER)
        if ("admin".equals(username) && "admin123".equals(password)) {

            String token = jwtUtil.generateToken(username);

            response.put("status", "success");
            response.put("token", token);
            response.put("role", "ADMIN");

            return response;
        }

        response.put("status", "failed");
        response.put("message", "Invalid credentials");

        return response;
    }
}