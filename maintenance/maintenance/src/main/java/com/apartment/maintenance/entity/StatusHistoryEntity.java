package com.apartment.maintenance.entity;

import com.apartment.maintenance.enums.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "status_history")
public class StatusHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "complaint_id", nullable = false)
    private ComplaintEntity complaint;

    @ManyToOne
    @JoinColumn(name = "changed_by", nullable = false)
    private UserEntity changedBy;

    @Enumerated(EnumType.STRING)
    private Status oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status newStatus;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(nullable = false)
    private LocalDateTime changedAt = LocalDateTime.now();

    public Long getHistoryId() { return historyId; }
    public void setHistoryId(Long historyId) { this.historyId = historyId; }

    public ComplaintEntity getComplaint() { return complaint; }
    public void setComplaint(ComplaintEntity complaint) { this.complaint = complaint; }

    public UserEntity getChangedBy() { return changedBy; }
    public void setChangedBy(UserEntity changedBy) { this.changedBy = changedBy; }

    public Status getOldStatus() { return oldStatus; }
    public void setOldStatus(Status oldStatus) { this.oldStatus = oldStatus; }

    public Status getNewStatus() { return newStatus; }
    public void setNewStatus(Status newStatus) { this.newStatus = newStatus; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }
}