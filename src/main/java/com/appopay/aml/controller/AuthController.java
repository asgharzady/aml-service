package com.appopay.aml.controller;


import com.appopay.aml.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private static final String STATIC_USERNAME = "admin";
    private static final String STATIC_PASSWORD = "password";

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        if (STATIC_USERNAME.equals(username) && STATIC_PASSWORD.equals(password)) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok().body("Bearer " + token);
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}
