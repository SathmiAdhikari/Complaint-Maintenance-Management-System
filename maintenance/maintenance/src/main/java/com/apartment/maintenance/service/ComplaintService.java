package com.apartment.maintenance.service;

import com.apartment.maintenance.entity.ComplaintEntity;
import com.apartment.maintenance.entity.StatusHistoryEntity;
import com.apartment.maintenance.entity.UserEntity;
import com.apartment.maintenance.entity.NotificationEntity;
import com.apartment.maintenance.enums.Category;
import com.apartment.maintenance.enums.Priority;
import com.apartment.maintenance.enums.Status;
import com.apartment.maintenance.repository.ComplaintRepository;
import com.apartment.maintenance.repository.StatusHistoryRepository;
import com.apartment.maintenance.repository.NotificationRepository;
import com.apartment.maintenance.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final StatusHistoryRepository statusHistoryRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public ComplaintService(ComplaintRepository complaintRepository,
                            StatusHistoryRepository statusHistoryRepository,
                            NotificationRepository notificationRepository,
                            UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    // Resident submits a complaint
    public ComplaintEntity submitComplaint(Long residentId, String title,
                                           String description, String category,
                                           String priority, String location) {
        UserEntity resident = userRepository.findById(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found."));

        ComplaintEntity complaint = new ComplaintEntity();
        complaint.setTitle(title);
        complaint.setDescription(description);
        complaint.setCategory(Category.valueOf(category.toUpperCase()));
        complaint.setPriority(Priority.valueOf(priority.toUpperCase()));
        complaint.setLocation(location);
        complaint.setStatus(Status.SUBMITTED);
        complaint.setSubmittedBy(resident);
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setUpdatedAt(LocalDateTime.now());
        complaintRepository.save(complaint);

        // Save status history
        saveStatusHistory(complaint, null, Status.SUBMITTED, resident, "Complaint submitted");

        return complaint;
    }

    // Admin assigns worker to complaint
    public ComplaintEntity assignWorker(Long complaintId, Long workerId, Long adminId) {
        ComplaintEntity complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found."));

        UserEntity worker = userRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found."));

        UserEntity admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found."));

        Status oldStatus = complaint.getStatus();
        complaint.setAssignedTo(worker);
        complaint.setStatus(Status.ASSIGNED);
        complaint.setUpdatedAt(LocalDateTime.now());
        complaintRepository.save(complaint);

        // Save status history
        saveStatusHistory(complaint, oldStatus, Status.ASSIGNED, admin, "Worker assigned by admin");

        // Notify worker
        sendNotification(worker, "You have been assigned a new complaint: " + complaint.getTitle());

        // Notify resident
        sendNotification(complaint.getSubmittedBy(), "Your complaint has been assigned to a worker.");

        return complaint;
    }

    // Worker updates complaint status
    public ComplaintEntity updateStatus(Long complaintId, String newStatus,
                                        Long workerId, String remarks) {
        ComplaintEntity complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found."));

        UserEntity worker = userRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found."));

        Status oldStatus = complaint.getStatus();
        Status updatedStatus = Status.valueOf(newStatus.toUpperCase());

        complaint.setStatus(updatedStatus);
        complaint.setUpdatedAt(LocalDateTime.now());

        if (updatedStatus == Status.COMPLETED) {
            complaint.setResolvedAt(LocalDateTime.now());
        }

        complaintRepository.save(complaint);

        // Save status history
        saveStatusHistory(complaint, oldStatus, updatedStatus, worker, remarks);

        // Notify resident
        sendNotification(complaint.getSubmittedBy(),
                "Your complaint status updated to: " + updatedStatus);

        return complaint;
    }

    // Get all complaints
    public List<ComplaintEntity> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // Get complaints by resident
    public List<ComplaintEntity> getComplaintsByResident(Long residentId) {
        return complaintRepository.findBySubmittedByUserId(residentId);
    }

    // Get complaints by worker
    public List<ComplaintEntity> getComplaintsByWorker(Long workerId) {
        return complaintRepository.findByAssignedToUserId(workerId);
    }

    // Get single complaint
    public ComplaintEntity getComplaintById(Long complaintId) {
        return complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found."));
    }

    // Save status history
    private void saveStatusHistory(ComplaintEntity complaint, Status oldStatus,
                                    Status newStatus, UserEntity changedBy,
                                    String remarks) {
        StatusHistoryEntity history = new StatusHistoryEntity();
        history.setComplaint(complaint);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setChangedBy(changedBy);
        history.setRemarks(remarks);
        history.setChangedAt(LocalDateTime.now());
        statusHistoryRepository.save(history);
    }

    // Send notification
    private void sendNotification(UserEntity user, String message) {
        NotificationEntity notification = new NotificationEntity();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setRead(false);
        notificationRepository.save(notification);
    }
}