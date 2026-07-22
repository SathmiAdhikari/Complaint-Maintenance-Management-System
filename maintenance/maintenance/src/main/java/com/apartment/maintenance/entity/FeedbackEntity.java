package com.apartment.maintenance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @OneToOne
    @JoinColumn(name = "complaint_id", nullable = false, unique = true)
    private ComplaintEntity complaint;

    @ManyToOne
    @JoinColumn(name = "resident_id", nullable = false)
    private UserEntity resident;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private UserEntity worker;

    @Column(nullable = false)
    private int rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getFeedbackId() { return feedbackId; }
    public void setFeedbackId(Long feedbackId) { this.feedbackId = feedbackId; }

    public ComplaintEntity getComplaint() { return complaint; }
    public void setComplaint(ComplaintEntity complaint) { this.complaint = complaint; }

    public UserEntity getResident() { return resident; }
    public void setResident(UserEntity resident) { this.resident = resident; }

    public UserEntity getWorker() { return worker; }
    public void setWorker(UserEntity worker) { this.worker = worker; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}