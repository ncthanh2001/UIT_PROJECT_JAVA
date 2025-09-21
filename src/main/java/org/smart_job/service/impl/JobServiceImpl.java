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
    public List<Job> findByCountry(String country) throws Exception {
        return jobDao.findByCountry(country);
    }

    @Override
    public List<Job> findByCity(String city) throws Exception {
        return jobDao.findByCity(city);
    }

    @Override
    public List<Job> findByLocation(String country, String city) throws Exception {
        return jobDao.findByLocation(country, city);
    }

    @Override
    public List<Job> findByCompanyName(String companyName) throws Exception {
        return jobDao.findByCompanyName(companyName);
    }

    @Override
    public List<Job> findByCompanyNameContaining(String keyword) throws Exception {
        return jobDao.findByCompanyNameContaining(keyword);
    }

    @Override
    public List<Job> getAllJobs() throws Exception {
        return jobDao.findAll();
    }

    @Override
    public List<Job> findByTitleContaining(String keyword) throws Exception {
        return jobDao.findByTitleContaining(keyword);
    }

    @Override
    public List<Job> searchInDescription(String keyword) throws Exception {
        return jobDao.searchInDescription(keyword);
    }

    @Override
    public List<Job> searchJobs(String keyword) throws Exception {
        return jobDao.searchJobs(keyword);
    }

    @Override
    public List<Job> findActiveJobs() throws Exception {
        return jobDao.findActiveJobs();
    }

    @Override
    public List<Job> findExpiredJobs() throws Exception {
        return jobDao.findExpiredJobs();
    }

    @Override
    public List<Job> findJobsExpiringBefore(LocalDateTime date) throws Exception {
        return jobDao.findJobsExpiringBefore(date);
    }

    @Override
    public List<Job> findJobsBySkillId(Integer skillId) throws Exception {
        return jobDao.findJobsBySkillId(skillId);
    }

    @Override
    public List<Job> findJobsMatchingUserSkills(Integer userId) throws Exception {
        return jobDao.findJobsMatchingUserSkills(userId);
    }

    @Override
    public int countByCompanyName(String companyName) throws Exception {
        return jobDao.countByCompanyName(companyName);
    }

    @Override
    public List<String> getUniqueCompanies() throws Exception {
        return jobDao.getUniqueCompanies();
    }

    @Override
    public List<String> getUniqueLocations() throws Exception {
        return jobDao.getUniqueLocations();
    }

    @Override
    public List<Job> findRecentJobs(int limit) throws Exception {
        return jobDao.findRecentJobs(limit);
    }
}
