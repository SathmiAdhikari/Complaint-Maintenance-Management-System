package com.apartment.maintenance.model;

import com.apartment.maintenance.enums.ApprovalStatus;
import com.apartment.maintenance.enums.AvailabilityStatus;
import com.apartment.maintenance.enums.Role;
import com.apartment.maintenance.enums.Trade;
import com.apartment.maintenance.interfaces.Assignable;

public class Worker extends User implements Assignable {

    private Trade trade;
    private int experienceYears;
    private AvailabilityStatus availabilityStatus;
    private ApprovalStatus approvalStatus;
    private double averageRating;
    private String bio;

    public Worker() {
        super();
        setRole(Role.WORKER);
        this.approvalStatus = ApprovalStatus.PENDING;
        this.availabilityStatus = AvailabilityStatus.AVAILABLE;
        this.averageRating = 0.0;
    }

    public Worker(Long userId, String fullName, String email,
                  String password, String phoneNumber,
                  Trade trade, int experienceYears, String bio) {
        super(userId, fullName, email, password, phoneNumber, Role.WORKER);
        this.trade = trade;
        this.experienceYears = experienceYears;
        this.bio = bio;
        this.approvalStatus = ApprovalStatus.PENDING;
        this.availabilityStatus = AvailabilityStatus.AVAILABLE;
        this.averageRating = 0.0;
    }

    @Override
    public String getDashboardInfo() {
        return "Worker: " + getFullName() +
               " | Trade: " + trade +
               " | Rating: " + averageRating;
    }

    @Override
    public String viewAssignedJobs() {
        return getFullName() + " is viewing assigned jobs.";
    }

    @Override
    public String updateJobStatus() {
        return getFullName() + " is updating job status.";
    }

    public Trade getTrade() { return trade; }
    public void setTrade(Trade trade) { this.trade = trade; }

    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int y) { this.experienceYears = y; }

    public AvailabilityStatus getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(AvailabilityStatus s) {
        this.availabilityStatus = s;
    }

    public ApprovalStatus getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(ApprovalStatus s) {
        this.approvalStatus = s;
    }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double r) { this.averageRating = r; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}