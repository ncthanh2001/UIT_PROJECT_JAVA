package org.smart_job.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.dao.JobApplicationDAO;
import org.smart_job.dao.impl.JobApplicationDAOImpl;
import org.smart_job.entity.JobApplication;
import org.smart_job.entity.JobStatus;
import org.smart_job.service.JobApplicationService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JobApplicationServiceImpl implements JobApplicationService {
    private static final Logger logger = LogManager.getLogger(JobApplicationServiceImpl.class);
    private final JobApplicationDAO jobApplicationDAO;

    public JobApplicationServiceImpl() {
        this.jobApplicationDAO = new JobApplicationDAOImpl();
    }

    @Override
    public JobApplication insert(JobApplication entity) throws Exception {
        // Validate required fields
        if (entity.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (entity.getCustomCompany() == null || entity.getCustomCompany().trim().isEmpty()) {
            throw new IllegalArgumentException("Company name is required");
        }
        if (entity.getCustomTitle() == null || entity.getCustomTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Job title is required");
        }
        
        // Set default values
        if (entity.getApplicationDate() == null) {
            entity.setApplicationDate(LocalDateTime.now());
        }
        if (entity.getStatus() == null) {
            entity.setStatus(JobStatus.APPLIED);
        }
        
        logger.info("Creating new job application for user: {}, company: {}, title: {}", 
                   entity.getUserId(), entity.getCustomCompany(), entity.getCustomTitle());
        
        return jobApplicationDAO.insert(entity);
    }

    public JobApplication update(JobApplication entity) throws Exception {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Job application ID is required for update");
        }
        
        // Validate required fields
        if (entity.getCustomCompany() == null || entity.getCustomCompany().trim().isEmpty()) {
            throw new IllegalArgumentException("Company name is required");
        }
        if (entity.getCustomTitle() == null || entity.getCustomTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Job title is required");
        }
        
        logger.info("Updating job application ID: {}, company: {}, title: {}", 
                   entity.getId(), entity.getCustomCompany(), entity.getCustomTitle());
        
        return jobApplicationDAO.update(entity);
    }

    public int delete(Integer id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("Job application ID is required for deletion");
        }
        
        logger.info("Deleting job application ID: {}", id);
        return jobApplicationDAO.delete(id);
    }

    public List<JobApplication> findByUserId(Integer userId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        
        return jobApplicationDAO.findByUserId(userId);
    }

    public JobApplication findById(Integer id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("Job application ID is required");
        }
        
        return jobApplicationDAO.findById(id);
    }

    public List<JobApplication> searchJobApplications(Integer userId, String keyword) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        
        if (keyword == null || keyword.trim().isEmpty()) {
            return findByUserId(userId);
        }
        
        return jobApplicationDAO.searchByCompanyOrTitle(keyword.trim(), userId);
    }

    public List<JobApplication> filterByStatus(Integer userId, String statusFilter) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        
        List<JobApplication> allJobs = findByUserId(userId);
        
        if (statusFilter == null || statusFilter.equals("All Status")) {
            return allJobs;
        }
        
        // Convert display name to enum
        JobStatus status = null;
        for (JobStatus jobStatus : JobStatus.values()) {
            if (jobStatus.getDisplayName().equals(statusFilter)) {
                status = jobStatus;
                break;
            }
        }
        
        if (status != null) {
            final JobStatus finalStatus = status;
            return allJobs.stream()
                    .filter(job -> job.getStatus() == finalStatus)
                    .collect(Collectors.toList());
        } else {
            logger.warn("Invalid status filter: {}", statusFilter);
            return allJobs;
        }
    }

    public boolean updateStatus(Integer applicationId, JobStatus newStatus) throws Exception {
        if (applicationId == null) {
            throw new IllegalArgumentException("Job application ID is required");
        }
        if (newStatus == null) {
            throw new IllegalArgumentException("Status is required");
        }
        
        logger.info("Updating job application ID: {} to status: {}", applicationId, newStatus);
        return jobApplicationDAO.updateApplicationStatus(applicationId, newStatus);
    }

    public boolean isUserApleidJob(Integer userId, Integer jobId) throws Exception {
        List<JobApplication> existingApplications = jobApplicationDAO.findByUserId(userId);
        
        if (existingApplications == null || existingApplications.isEmpty()) {
            return false;
        }

        return existingApplications.stream()
                .anyMatch(app -> app.getJobId().equals(jobId));
    }

    @Override
    public Map<String, Object> getDashboardStats(Integer userId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        Map<String, Object> stats = new HashMap<>();
        List<JobApplication> allApplications = findByUserId(userId);

        stats.put("totalApplications", getTotalApplicationsCount(userId));
        stats.put("responseRate", getResponseRate(userId));
        stats.put("pendingResponses", getPendingResponsesCount(userId));
        stats.put("interviewInvites", getInterviewInvitesCount(userId));

        return stats;
    }

    @Override
    public int getTotalApplicationsCount(Integer userId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        List<JobApplication> applications = findByUserId(userId);
        return applications != null ? applications.size() : 0;
    }

    @Override
    public double getResponseRate(Integer userId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        List<JobApplication> applications = findByUserId(userId);
        if (applications == null || applications.isEmpty()) {
            return 0.0;
        }

        // Count applications that have received responses (not APPLIED or WISH_LIST)
        long responsesReceived = applications.stream()
                .filter(app -> app.getStatus() != JobStatus.APPLIED &&
                              app.getStatus() != JobStatus.WISH_LIST)
                .count();

        return (double) responsesReceived / applications.size() * 100;
    }

    @Override
    public int getPendingResponsesCount(Integer userId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        List<JobApplication> applications = findByUserId(userId);
        if (applications == null || applications.isEmpty()) {
            return 0;
        }

        // Count applications that are still pending (APPLIED status)
        return (int) applications.stream()
                .filter(app -> app.getStatus() == JobStatus.APPLIED)
                .count();
    }

    @Override
    public int getInterviewInvitesCount(Integer userId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        List<JobApplication> applications = findByUserId(userId);
        if (applications == null || applications.isEmpty()) {
            return 0;
        }

        // Count applications with interview invites (INTERVIEW status)
        return (int) applications.stream()
                .filter(app -> app.getStatus() == JobStatus.INTERVIEW)
                .count();
    }
}