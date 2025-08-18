package org.smart_job.dao;

import org.smart_job.entity.UserSkill;

import java.util.List;

public interface UserSkillDAO extends GenericDAO<UserSkill, Integer> {
    // Basic junction operations
    boolean addSkillToUser(Integer userId, Integer skillId) throws Exception;
    boolean removeSkillFromUser(Integer userId, Integer skillId) throws Exception;
    boolean userHasSkill(Integer userId, Integer skillId) throws Exception;

    // Bulk operations
    void addSkillsToUser(Integer userId, List<Integer> skillIds) throws Exception;
    void removeAllSkillsFromUser(Integer userId) throws Exception;
    void replaceUserSkills(Integer userId, List<Integer> skillIds) throws Exception;

    // Query operations
    List<UserSkill> findByUserId(Integer userId) throws Exception;
    List<UserSkill> findBySkillId(Integer skillId) throws Exception;
    List<Integer> findUserIdsBySkillId(Integer skillId) throws Exception;
    List<Integer> findSkillIdsByUserId(Integer userId) throws Exception;

    // Analytics
    int countUsersBySkillId(Integer skillId) throws Exception;
    int countSkillsByUserId(Integer userId) throws Exception;
    List<Object[]> getUserSkillCounts() throws Exception; // [userId, skillCount]
    List<Object[]> getSkillPopularityStats() throws Exception; // [skillId, userCount]

    // Advanced queries
    List<Integer> findUsersWithSimilarSkills(Integer userId, int minCommonSkills) throws Exception;
    List<Integer> findCommonSkillsBetweenUsers(Integer userId1, Integer userId2) throws Exception;
}
