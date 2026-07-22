package com.apartment.maintenance.model;

import com.apartment.maintenance.enums.Role;
import com.apartment.maintenance.interfaces.Reviewable;
import com.apartment.maintenance.interfaces.Submittable;

public class Resident extends User
        implements Submittable, Reviewable {

    private String apartmentNumber;
    private String block;

    public Resident() {
        super();
        setRole(Role.RESIDENT);
    }

    public Resident(Long userId, String fullName, String email,
                    String password, String phoneNumber,
                    String apartmentNumber, String block) {
        super(userId, fullName, email, password, phoneNumber, Role.RESIDENT);
        this.apartmentNumber = apartmentNumber;
        this.block = block;
    }

    @Override
    public String getDashboardInfo() {
        return "Resident: " + getFullName() +
               " | Apartment: " + block + "-" + apartmentNumber;
    }

    @Override
    public String submitComplaint() {
        return getFullName() + " is submitting a complaint.";
    }

    @Override
    public String submitFeedback() {
        return getFullName() + " is submitting feedback.";
    }

    public String getApartmentNumber() { return apartmentNumber; }
    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getBlock() { return block; }
    public void setBlock(String block) { this.block = block; }
}