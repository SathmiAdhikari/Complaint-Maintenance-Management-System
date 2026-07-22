package com.apartment.maintenance.repository;

import com.apartment.maintenance.entity.UserEntity;
import com.apartment.maintenance.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findByRole(Role role);
    boolean existsByEmail(String email);
}