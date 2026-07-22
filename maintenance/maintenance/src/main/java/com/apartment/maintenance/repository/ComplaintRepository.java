package com.apartment.maintenance.repository;

import com.apartment.maintenance.entity.ComplaintEntity;
import com.apartment.maintenance.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplaintRepository
        extends JpaRepository<ComplaintEntity, Long> {
    List<ComplaintEntity> findBySubmittedByUserId(Long userId);
    List<ComplaintEntity> findByAssignedToUserId(Long userId);
    List<ComplaintEntity> findByStatus(Status status);
}