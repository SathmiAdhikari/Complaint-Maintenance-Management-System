package com.apartment.maintenance.controller;

import com.apartment.maintenance.entity.FeedbackEntity;
import com.apartment.maintenance.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // Submit feedback
    @PostMapping
    public ResponseEntity<?> submitFeedback(@RequestBody Map<String, String> request) {
        try {
            FeedbackEntity feedback = feedbackService.submitFeedback(
                Long.parseLong(request.get("complaintId")),
                Long.parseLong(request.get("residentId")),
                Integer.parseInt(request.get("rating")),
                request.get("comment")
            );
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Feedback submitted successfully");
            response.put("feedbackId", feedback.getFeedbackId());
            response.put("rating", feedback.getRating());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Get feedback by worker
    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<FeedbackEntity>> getByWorker(
            @PathVariable Long workerId) {
        return ResponseEntity.ok(
            feedbackService.getFeedbackByWorker(workerId));
    }
}