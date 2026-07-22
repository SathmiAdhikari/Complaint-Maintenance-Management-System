package com.apartment.maintenance.controller;

import com.apartment.maintenance.entity.UserEntity;
import com.apartment.maintenance.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Resident Registration
    @PostMapping("/register/resident")
    public ResponseEntity<?> registerResident(@RequestBody Map<String, String> request) {
        try {
            UserEntity user = authService.registerResident(
                request.get("fullName"),
                request.get("email"),
                request.get("password"),
                request.get("phoneNumber"),
                request.get("apartmentNumber"),
                request.get("block")
            );
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Resident registered successfully");
            response.put("userId", user.getUserId());
            response.put("role", user.getRole());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Worker Registration
    @PostMapping("/register/worker")
    public ResponseEntity<?> registerWorker(@RequestBody Map<String, String> request) {
        try {
            UserEntity user = authService.registerWorker(
                request.get("fullName"),
                request.get("email"),
                request.get("password"),
                request.get("phoneNumber"),
                request.get("trade"),
                Integer.parseInt(request.get("experienceYears")),
                request.get("bio")
            );
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Worker registered. Waiting for admin approval.");
            response.put("userId", user.getUserId());
            response.put("role", user.getRole());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            UserEntity user = authService.findByEmail(request.get("email"));

            if (!authService.checkPassword(request.get("password"), user.getPassword())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Invalid password");
                return ResponseEntity.badRequest().body(error);
            }

            if (!user.isActive()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Account not active. Waiting for admin approval.");
                return ResponseEntity.badRequest().body(error);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("userId", user.getUserId());
            response.put("fullName", user.getFullName());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // TEMPORARY - for generating password hashes, remove after use
@GetMapping("/hash")
public ResponseEntity<?> generateHash(@RequestParam String password) {
    org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder =
        new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    Map<String, String> response = new HashMap<>();
    response.put("hash", encoder.encode(password));
    return ResponseEntity.ok(response);
}
}