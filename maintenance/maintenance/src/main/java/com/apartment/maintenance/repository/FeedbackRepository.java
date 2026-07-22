package com.apartment.maintenance.repository;

import com.apartment.maintenance.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository
        extends JpaRepository<FeedbackEntity, Long> {
    Optional<FeedbackEntity> findByComplaintComplaintId(Long complaintId);
    List<FeedbackEntity> findByWorkerUserId(Long workerId);
}