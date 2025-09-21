package org.smart_job.service.impl;

import org.smart_job.dao.JobDAO;
import org.smart_job.dao.impl.JobDAOImpl;
import org.smart_job.entity.Job;
import org.smart_job.service.JobService;

import java.time.LocalDateTime;
import java.util.List;

public class JobServiceImpl implements JobService {
    private final JobDAO jobDao = new JobDAOImpl();

    @Override
    public List<Job> getAllJobs() throws Exception {
        return jobDao.findAll();
    }

    @Override
    public Job getJobById(Integer id) throws Exception {
        return jobDao.findById(id);
    }

    @Override
    public Job createJob(Job job) throws Exception {
        return jobDao.insert(job);
    }

    @Override
    public void updateJob(Job job) throws Exception {
        jobDao.update(job);
    }

    @Override
    public void deleteJob(Integer id) throws Exception {
        jobDao.delete(id);
    }

    @Override
    public List<Job> searchJobs(String keyword) throws Exception {
        return jobDao.searchJobs(keyword);
    }

    @Override
    public List<Job> getActiveJobs() throws Exception {
        return jobDao.findActiveJobs();
    }

    @Override
    public List<Job> getExpiredJobs() throws Exception {
        return jobDao.findExpiredJobs();
    }

    @Override
    public List<Job> getJobsExpiringBefore(LocalDateTime date) throws Exception {
        return jobDao.findJobsExpiringBefore(date);
    }

    @Override
    public List<Job> getRecentJobs(int limit) throws Exception {
        return jobDao.findRecentJobs(limit);
    }
}
