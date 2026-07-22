package com.apartment.maintenance.model;

import com.apartment.maintenance.enums.Role;
import com.apartment.maintenance.interfaces.Manageable;

public class Admin extends User implements Manageable {

    public Admin() {
        super();
        setRole(Role.ADMIN);
    }

    public Admin(Long userId, String fullName, String email,
                 String password, String phoneNumber) {
        super(userId, fullName, email, password, phoneNumber, Role.ADMIN);
    }

    @Override
    public String getDashboardInfo() {
        return "Admin: " + getFullName() + " | Full system access";
    }

    @Override
    public String approveWorker() {
        return getFullName() + " is approving a worker.";
    }

    @Override
    public String assignWorker() {
        return getFullName() + " is assigning a worker.";
    }

    @Override
    public String viewAllComplaints() {
        return getFullName() + " is viewing all complaints.";
    }
}