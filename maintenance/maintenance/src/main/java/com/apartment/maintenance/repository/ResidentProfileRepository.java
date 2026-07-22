package com.apartment.maintenance.repository;

import com.apartment.maintenance.entity.ResidentProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ResidentProfileRepository
        extends JpaRepository<ResidentProfileEntity, Long> {
    Optional<ResidentProfileEntity> findByUserUserId(Long userId);
}