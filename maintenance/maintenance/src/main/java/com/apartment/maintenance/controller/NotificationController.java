package com.apartment.maintenance.controller;

import com.apartment.maintenance.entity.NotificationEntity;
import com.apartment.maintenance.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Get notifications for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationEntity>> getNotifications(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
            notificationService.getNotifications(userId));
    }

    // Mark as read
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
        try {
            notificationService.markAsRead(notificationId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Notification marked as read");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Get unread count
    @GetMapping("/{userId}/unread")
    public ResponseEntity<?> getUnreadCount(@PathVariable Long userId) {
        Map<String, Integer> response = new HashMap<>();
        response.put("unreadCount", notificationService.getUnreadCount(userId));
        return ResponseEntity.ok(response);
    }
}