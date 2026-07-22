package com.apartment.maintenance.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "resident_profiles")
public class ResidentProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Column(length = 20)
    private String apartmentNumber;

    @Column(length = 20)
    private String block;

    public Long getProfileId() { return profileId; }
    public void setProfileId(Long profileId) { this.profileId = profileId; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public String getApartmentNumber() { return apartmentNumber; }
    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getBlock() { return block; }
    public void setBlock(String block) { this.block = block; }
}