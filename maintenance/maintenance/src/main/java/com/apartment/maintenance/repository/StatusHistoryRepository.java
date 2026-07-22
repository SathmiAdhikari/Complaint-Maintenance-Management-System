package com.apartment.maintenance.repository;

import com.apartment.maintenance.entity.StatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StatusHistoryRepository
        extends JpaRepository<StatusHistoryEntity, Long> {
    List<StatusHistoryEntity> findByComplaintComplaintIdOrderByChangedAtAsc(
        Long complaintId);
}