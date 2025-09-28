package org.smart_job.service;

import org.smart_job.entity.JobApplication;
import org.smart_job.entity.JobStatus;

import java.util.List;

public interface JobApplicationService {
    JobApplication insert(JobApplication entity) throws Exception;
    JobApplication update(JobApplication entity) throws Exception;
    int delete(Integer id) throws Exception;
    List<JobApplication> findByUserId(Integer userId) throws Exception;
    JobApplication findById(Integer id) throws Exception;
    List<JobApplication> searchJobApplications(Integer userId, String keyword) throws Exception;
    List<JobApplication> filterByStatus(Integer userId, String statusFilter) throws Exception;
    boolean updateStatus(Integer applicationId, JobStatus newStatus) throws Exception;
    boolean isUserApleidJob(Integer userId, Integer jobId) throws Exception;
}
