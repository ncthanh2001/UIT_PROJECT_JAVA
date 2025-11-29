package org.smart_job.dao;

import org.smart_job.entity.JobSkill;

import java.util.List;

public interface JobSkillDAO extends GenericDAO<JobSkill, Integer> {
    // Basic junction operations
    boolean addSkillToJob(Integer jobId, Integer skillId) throws Exception;
    boolean removeSkillFromJob(Integer jobId, Integer skillId) throws Exception;
    boolean jobRequiresSkill(Integer jobId, Integer skillId) throws Exception;

    // Bulk operations
    void addSkillsToJob(Integer jobId, List<Integer> skillIds) throws Exception;
    void removeAllSkillsFromJob(Integer jobId) throws Exception;
    void replaceJobSkills(Integer jobId, List<Integer> skillIds) throws Exception;

    // Query operations
    List<JobSkill> findByJobId(Integer jobId) throws Exception;
    List<JobSkill> findBySkillId(Integer skillId) throws Exception;
    List<Integer> findJobIdsBySkillId(Integer skillId) throws Exception;
    List<Integer> findSkillIdsByJobId(Integer jobId) throws Exception;

    // Analytics
    int countJobsBySkillId(Integer skillId) throws Exception;
    int countSkillsByJobId(Integer jobId) throws Exception;
    List<Object[]> getJobSkillCounts() throws Exception; // [jobId, skillCount]
    List<Object[]> getSkillDemandStats() throws Exception; // [skillId, jobCount]

    // Advanced queries
    List<Integer> findJobsWithSimilarSkills(Integer jobId, int minCommonSkills) throws Exception;
    List<Integer> findCommonSkillsBetweenJobs(Integer jobId1, Integer jobId2) throws Exception;
    List<Integer> findJobsMatchingSkills(List<Integer> skillIds, int minMatchCount) throws Exception;
}