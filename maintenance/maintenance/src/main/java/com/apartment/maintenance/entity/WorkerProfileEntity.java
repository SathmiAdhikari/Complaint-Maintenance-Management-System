package com.apartment.maintenance.entity;

import com.apartment.maintenance.enums.ApprovalStatus;
import com.apartment.maintenance.enums.AvailabilityStatus;
import com.apartment.maintenance.enums.Trade;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "worker_profiles")
public class WorkerProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Trade trade;

    @Column(nullable = false)
    private int experienceYears = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @Column(precision = 3, scale = 2)
    private BigDecimal averageRating = BigDecimal.ZERO;

    @Column(columnDefinition = "TEXT")
    private String bio;

    public Long getProfileId() { return profileId; }
    public void setProfileId(Long profileId) { this.profileId = profileId; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public Trade getTrade() { return trade; }
    public void setTrade(Trade trade) { this.trade = trade; }

    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public AvailabilityStatus getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(AvailabilityStatus s) {
        this.availabilityStatus = s;
    }

    public ApprovalStatus getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(ApprovalStatus s) {
        this.approvalStatus = s;
    }

    public BigDecimal getAverageRating() { return averageRating; }
    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}