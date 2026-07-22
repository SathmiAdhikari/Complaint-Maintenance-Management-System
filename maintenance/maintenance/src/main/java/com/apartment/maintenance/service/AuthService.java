package com.apartment.maintenance.service;

import com.apartment.maintenance.entity.ResidentProfileEntity;
import com.apartment.maintenance.entity.UserEntity;
import com.apartment.maintenance.entity.WorkerProfileEntity;
import com.apartment.maintenance.enums.ApprovalStatus;
import com.apartment.maintenance.enums.AvailabilityStatus;
import com.apartment.maintenance.enums.Role;
import com.apartment.maintenance.enums.Trade;
import com.apartment.maintenance.repository.ResidentProfileRepository;
import com.apartment.maintenance.repository.UserRepository;
import com.apartment.maintenance.repository.WorkerProfileRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ResidentProfileRepository residentProfileRepository;
    private final WorkerProfileRepository workerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       ResidentProfileRepository residentProfileRepository,
                       WorkerProfileRepository workerProfileRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.residentProfileRepository = residentProfileRepository;
        this.workerProfileRepository = workerProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new resident
    public UserEntity registerResident(String fullName, String email,
                                       String password, String phoneNumber,
                                       String apartmentNumber, String block) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered.");
        }

        UserEntity user = new UserEntity();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);
        user.setRole(Role.RESIDENT);
        user.setActive(true);
        userRepository.save(user);

        ResidentProfileEntity profile = new ResidentProfileEntity();
        profile.setUser(user);
        profile.setApartmentNumber(apartmentNumber);
        profile.setBlock(block);
        residentProfileRepository.save(profile);

        return user;
    }

    // Register a new worker
    public UserEntity registerWorker(String fullName, String email,
                                     String password, String phoneNumber,
                                     String trade, int experienceYears,
                                     String bio) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered.");
        }

        UserEntity user = new UserEntity();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);
        user.setRole(Role.WORKER);
        user.setActive(false);
        userRepository.save(user);

        WorkerProfileEntity profile = new WorkerProfileEntity();
        profile.setUser(user);
        profile.setTrade(Trade.valueOf(trade.toUpperCase()));
        profile.setExperienceYears(experienceYears);
        profile.setBio(bio);
        profile.setApprovalStatus(ApprovalStatus.PENDING);
        profile.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        workerProfileRepository.save(profile);

        return user;
    }

    // Find user by email for login
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    // Check password matches
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}