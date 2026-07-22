package com.apartment.maintenance.service;

import com.apartment.maintenance.entity.ComplaintEntity;
import com.apartment.maintenance.entity.FeedbackEntity;
import com.apartment.maintenance.entity.UserEntity;
import com.apartment.maintenance.entity.WorkerProfileEntity;
import com.apartment.maintenance.enums.Status;
import com.apartment.maintenance.repository.ComplaintRepository;
import com.apartment.maintenance.repository.FeedbackRepository;
import com.apartment.maintenance.repository.UserRepository;
import com.apartment.maintenance.repository.WorkerProfileRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final WorkerProfileRepository workerProfileRepository;

    public FeedbackService(FeedbackRepository feedbackRepository,
                           ComplaintRepository complaintRepository,
                           UserRepository userRepository,
                           WorkerProfileRepository workerProfileRepository) {
        this.feedbackRepository = feedbackRepository;
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
        this.workerProfileRepository = workerProfileRepository;
    }

    // Resident submits feedback
    public FeedbackEntity submitFeedback(Long complaintId, Long residentId,
                                          int rating, String comment) {
        ComplaintEntity complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found."));

        if (complaint.getStatus() != Status.COMPLETED) {
            throw new RuntimeException("Feedback only allowed for completed complaints.");
        }

        if (feedbackRepository.findByComplaintComplaintId(complaintId).isPresent()) {
            throw new RuntimeException("Feedback already submitted for this complaint.");
        }

        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5.");
        }

        UserEntity resident = userRepository.findById(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found."));

        FeedbackEntity feedback = new FeedbackEntity();
        feedback.setComplaint(complaint);
        feedback.setResident(resident);
        feedback.setWorker(complaint.getAssignedTo());
        feedback.setRating(rating);
        feedback.setComment(comment);
        feedback.setCreatedAt(LocalDateTime.now());
        feedbackRepository.save(feedback);

        // Update worker average rating
        updateWorkerRating(complaint.getAssignedTo().getUserId());

        return feedback;
    }

    // Update worker average rating
    private void updateWorkerRating(Long workerId) {
        List<FeedbackEntity> feedbacks = feedbackRepository.findByWorkerUserId(workerId);

        double average = feedbacks.stream()
                .mapToInt(FeedbackEntity::getRating)
                .average()
                .orElse(0.0);

        WorkerProfileEntity profile = workerProfileRepository
                .findByUserUserId(workerId)
                .orElseThrow(() -> new RuntimeException("Worker profile not found."));

        profile.setAverageRating(
            BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP)
        );
        workerProfileRepository.save(profile);
    }

    // Get feedback by worker
    public List<FeedbackEntity> getFeedbackByWorker(Long workerId) {
        return feedbackRepository.findByWorkerUserId(workerId);
    }
}