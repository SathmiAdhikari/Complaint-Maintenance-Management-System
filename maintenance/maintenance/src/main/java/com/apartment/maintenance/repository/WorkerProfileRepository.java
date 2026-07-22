package com.apartment.maintenance.repository;

import com.apartment.maintenance.entity.WorkerProfileEntity;
import com.apartment.maintenance.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerProfileRepository
        extends JpaRepository<WorkerProfileEntity, Long> {
    Optional<WorkerProfileEntity> findByUserUserId(Long userId);
    List<WorkerProfileEntity> findByApprovalStatus(ApprovalStatus status);
}