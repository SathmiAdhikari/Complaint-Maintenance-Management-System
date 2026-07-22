package com.apartment.maintenance.controller;

import com.apartment.maintenance.entity.UserEntity;
import com.apartment.maintenance.entity.WorkerProfileEntity;
import com.apartment.maintenance.enums.ApprovalStatus;
import com.apartment.maintenance.enums.Role;
import com.apartment.maintenance.repository.UserRepository;
import com.apartment.maintenance.repository.WorkerProfileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserRepository userRepository;
    private final WorkerProfileRepository workerProfileRepository;

    public AdminController(UserRepository userRepository,
                           WorkerProfileRepository workerProfileRepository) {
        this.userRepository = userRepository;
        this.workerProfileRepository = workerProfileRepository;
    }

    @GetMapping("/api/workers/all")
    public ResponseEntity<?> getAllWorkers() {
        List<UserEntity> workers = userRepository.findByRole(Role.WORKER);
        return ResponseEntity.ok(workers.stream().map(w -> {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", w.getUserId());
            map.put("fullName", w.getFullName());
            map.put("email", w.getEmail());
            map.put("phoneNumber", w.getPhoneNumber());
            map.put("active", w.isActive());
            map.put("createdAt", w.getCreatedAt());
            workerProfileRepository.findByUserUserId(w.getUserId())
                .ifPresent(p -> {
                    map.put("trade", p.getTrade());
                    map.put("experienceYears", p.getExperienceYears());
                    map.put("approvalStatus", p.getApprovalStatus());
                    map.put("availabilityStatus", p.getAvailabilityStatus());
                    map.put("averageRating", p.getAverageRating());
                    map.put("bio", p.getBio());
                });
            return map;
        }).toList());
    }

    @GetMapping("/api/workers/pending")
    public ResponseEntity<?> getPendingWorkers() {
        List<WorkerProfileEntity> pending = workerProfileRepository
            .findByApprovalStatus(ApprovalStatus.PENDING);
        return ResponseEntity.ok(pending.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", p.getUser().getUserId());
            map.put("fullName", p.getUser().getFullName());
            map.put("email", p.getUser().getEmail());
            map.put("phoneNumber", p.getUser().getPhoneNumber());
            map.put("trade", p.getTrade());
            map.put("experienceYears", p.getExperienceYears());
            map.put("approvalStatus", p.getApprovalStatus());
            map.put("bio", p.getBio());
            return map;
        }).toList());
    }

    @GetMapping("/api/workers/approved")
    public ResponseEntity<?> getApprovedWorkers() {
        List<WorkerProfileEntity> approved = workerProfileRepository
            .findByApprovalStatus(ApprovalStatus.APPROVED);
        return ResponseEntity.ok(approved.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", p.getUser().getUserId());
            map.put("fullName", p.getUser().getFullName());
            map.put("email", p.getUser().getEmail());
            map.put("trade", p.getTrade());
            map.put("experienceYears", p.getExperienceYears());
            map.put("averageRating", p.getAverageRating());
            map.put("availabilityStatus", p.getAvailabilityStatus());
            return map;
        }).toList());
    }

    @PatchMapping("/api/admin/workers/{workerId}/approve")
    public ResponseEntity<?> approveWorker(@PathVariable Long workerId) {
        try {
            WorkerProfileEntity profile = workerProfileRepository
                .findByUserUserId(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
            profile.setApprovalStatus(ApprovalStatus.APPROVED);
            workerProfileRepository.save(profile);
            UserEntity user = profile.getUser();
            user.setActive(true);
            userRepository.save(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Worker approved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PatchMapping("/api/admin/workers/{workerId}/reject")
    public ResponseEntity<?> rejectWorker(@PathVariable Long workerId) {
        try {
            WorkerProfileEntity profile = workerProfileRepository
                .findByUserUserId(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
            profile.setApprovalStatus(ApprovalStatus.REJECTED);
            workerProfileRepository.save(profile);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Worker rejected");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/api/residents/all")
    public ResponseEntity<?> getAllResidents() {
        List<UserEntity> residents = userRepository.findByRole(Role.RESIDENT);
        return ResponseEntity.ok(residents.stream().map(r -> {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", r.getUserId());
            map.put("fullName", r.getFullName());
            map.put("email", r.getEmail());
            map.put("phoneNumber", r.getPhoneNumber());
            map.put("active", r.isActive());
            map.put("createdAt", r.getCreatedAt());
            return map;
        }).toList());
    }
}