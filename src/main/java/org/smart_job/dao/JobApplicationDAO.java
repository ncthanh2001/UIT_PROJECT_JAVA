package org.smart_job.dao;


import org.smart_job.entity.JobApplication;
import org.smart_job.entity.JobStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface JobApplicationDAO extends GenericDAO<JobApplication, Integer> {
    // User-based queries
    List<JobApplication> findByUserId(Integer userId) throws Exception;
    List<JobApplication> findByUserIdAndStatus(Integer userId, JobStatus status) throws Exception;
    List<JobApplication> findByUserIdOrderByApplicationDate(Integer userId, boolean ascending) throws Exception;

    // Job-based queries
    List<JobApplication> findByJobId(Integer jobId) throws Exception;
    int countApplicationsByJobId(Integer jobId) throws Exception;

    // Status-based queries
    List<JobApplication> findByStatus(JobStatus status) throws Exception;
    List<JobApplication> findApplicationsInStatuses(List<JobStatus> statuses) throws Exception;

    // Date-based queries
    List<JobApplication> findApplicationsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) throws Exception;
    List<JobApplication> findRecentApplications(int limit) throws Exception;
    List<JobApplication> findApplicationsAppliedToday() throws Exception;

    // Custom applications (job_id is null)
    List<JobApplication> findCustomApplicationsByUserId(Integer userId) throws Exception;
    List<JobApplication> findJobPostingApplicationsByUserId(Integer userId) throws Exception;

    // Analytics and statistics
    Map<JobStatus, Integer> getApplicationStatusCounts(Integer userId) throws Exception;
    List<JobApplication> findApplicationsNeedingFollowUp() throws Exception;

    // Company-based queries
    List<JobApplication> findByCompanyName(String companyName, Integer userId) throws Exception;
    List<JobApplication> searchByCompanyOrTitle(String keyword, Integer userId) throws Exception;

    // Update status
    boolean updateApplicationStatus(Integer applicationId, JobStatus newStatus) throws Exception;
    boolean updateApplicationNotes(Integer applicationId, String notes) throws Exception;
}