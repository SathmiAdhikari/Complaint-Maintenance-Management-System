package com.apartment.maintenance.controller;

import com.apartment.maintenance.entity.ComplaintEntity;
import com.apartment.maintenance.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    // Submit complaint
    @PostMapping
    public ResponseEntity<?> submitComplaint(@RequestBody Map<String, String> request) {
        try {
            ComplaintEntity complaint = complaintService.submitComplaint(
                Long.parseLong(request.get("residentId")),
                request.get("title"),
                request.get("description"),
                request.get("category"),
                request.get("priority"),
                request.get("location")
            );
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Complaint submitted successfully");
            response.put("complaintId", complaint.getComplaintId());
            response.put("status", complaint.getStatus());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Get all complaints (Admin)
    @GetMapping
    public ResponseEntity<List<ComplaintEntity>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    // Get complaints by resident
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<ComplaintEntity>> getByResident(
            @PathVariable Long residentId) {
        return ResponseEntity.ok(
            complaintService.getComplaintsByResident(residentId));
    }

    // Get complaints by worker
    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<ComplaintEntity>> getByWorker(
            @PathVariable Long workerId) {
        return ResponseEntity.ok(
            complaintService.getComplaintsByWorker(workerId));
    }

    // Get single complaint
    @GetMapping("/{complaintId}")
    public ResponseEntity<?> getComplaint(@PathVariable Long complaintId) {
        try {
            return ResponseEntity.ok(
                complaintService.getComplaintById(complaintId));
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Assign worker (Admin)
    @PatchMapping("/{complaintId}/assign")
    public ResponseEntity<?> assignWorker(
            @PathVariable Long complaintId,
            @RequestBody Map<String, String> request) {
        try {
            ComplaintEntity complaint = complaintService.assignWorker(
                complaintId,
                Long.parseLong(request.get("workerId")),
                Long.parseLong(request.get("adminId"))
            );
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Worker assigned successfully");
            response.put("status", complaint.getStatus());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Update status (Worker)
    @PatchMapping("/{complaintId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long complaintId,
            @RequestBody Map<String, String> request) {
        try {
            ComplaintEntity complaint = complaintService.updateStatus(
                complaintId,
                request.get("status"),
                Long.parseLong(request.get("workerId")),
                request.get("remarks")
            );
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Status updated successfully");
            response.put("status", complaint.getStatus());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}