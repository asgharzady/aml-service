package com.appopay.aml.controller;


import com.appopay.aml.util.JwtUtil;
import io.jsonwebtoken.JwtException;
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

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }

        String oldToken = authorizationHeader.substring(7); // Remove "Bearer " prefix
        String username;

        try {
            // Extract username even from expired tokens
            username = jwtUtil.extractUsernameFromExpiredToken(oldToken);
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(403).body("Invalid token");
        }

        // Generate a new token
        String newToken = jwtUtil.generateToken(username);

        return ResponseEntity.ok(newToken);
    }
}
