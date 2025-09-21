package org.smart_job.dao;

import org.smart_job.entity.Job;
import java.time.LocalDateTime;
import java.util.List;

public interface JobDAO extends GenericDAO<Job, Integer> {
    // Location-based queries
    List<Job> findByCountry(String country) throws Exception;
    List<Job> findByCity(String city) throws Exception;
    List<Job> findByLocation(String country, String city) throws Exception;

    // Company-based queries
    List<Job> findByCompanyName(String companyName) throws Exception;
    List<Job> findByCompanyNameContaining(String keyword) throws Exception;

    // Title and description search
    List<Job> findByTitleContaining(String keyword) throws Exception;
    List<Job> searchInDescription(String keyword) throws Exception;
    List<Job> searchJobs(String keyword) throws Exception; // Combined search

    // Expiration and status
    List<Job> findActiveJobs() throws Exception;
    List<Job> findExpiredJobs() throws Exception;
    List<Job> findJobsExpiringBefore(LocalDateTime date) throws Exception;

    // Skill-based matching
    List<Job> findJobsBySkillId(Integer skillId) throws Exception;
    List<Job> findJobsMatchingUserSkills(Integer userId) throws Exception;

    // Statistics and analytics
    int countByCompanyName(String companyName) throws Exception;
    List<String> getUniqueCompanies() throws Exception;
    List<String> getUniqueLocations() throws Exception;

    // Recent jobs
    List<Job> findRecentJobs(int limit) throws Exception;

    // Pagination queries
    List<Job> findJobs(String keyword, String country, String city, int page, int pageSize) throws Exception;
    int countJobs(String keyword, String country, String city) throws Exception;

}

