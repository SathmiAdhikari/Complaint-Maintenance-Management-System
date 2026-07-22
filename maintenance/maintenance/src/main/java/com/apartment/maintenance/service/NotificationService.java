package com.apartment.maintenance.service;

import com.apartment.maintenance.entity.NotificationEntity;
import com.apartment.maintenance.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationEntity> getNotifications(Long userId) {
        return notificationRepository
                .findByUserUserIdOrderByCreatedAtDesc(userId);
    }

    public void markAsRead(Long notificationId) {
        NotificationEntity notification = notificationRepository
                .findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found."));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public int getUnreadCount(Long userId) {
        return notificationRepository
                .findByUserUserIdAndIsRead(userId, false).size();
    }
}