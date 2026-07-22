package com.apartment.maintenance.model;

import com.apartment.maintenance.enums.Role;
import java.time.LocalDateTime;

public abstract class User {

    private Long userId;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
    private boolean isActive;
    private LocalDateTime createdAt;

    public User() {
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    public User(Long userId, String fullName, String email,
                String password, String phoneNumber, Role role) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    // Abstract method - each child implements differently
    // This is Polymorphism
    public abstract String getDashboardInfo();

    // Getters and Setters - Encapsulation
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}