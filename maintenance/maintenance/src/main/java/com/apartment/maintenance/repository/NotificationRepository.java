package com.apartment.maintenance.repository;

import com.apartment.maintenance.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository
        extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByUserUserIdOrderByCreatedAtDesc(Long userId);
    List<NotificationEntity> findByUserUserIdAndIsRead(
        Long userId, boolean isRead);
}