package org.smart_job.service;

import org.smart_job.entity.Job;

import java.time.LocalDateTime;
import java.util.List;

public interface JobService {
    List<Job> getAllJobs() throws Exception;
    Job getJobById(Integer id) throws Exception;
    Job createJob(Job job) throws Exception;
    void updateJob(Job job) throws Exception;
    void deleteJob(Integer id) throws Exception;

    List<Job> searchJobs(String keyword) throws Exception;
    List<Job> getActiveJobs() throws Exception;
    List<Job> getExpiredJobs() throws Exception;
    List<Job> getJobsExpiringBefore(LocalDateTime date) throws Exception;
    List<Job> getRecentJobs(int limit) throws Exception;
}
