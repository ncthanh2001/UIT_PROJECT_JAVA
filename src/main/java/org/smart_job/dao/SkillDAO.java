package org.smart_job.dao;

import org.smart_job.entity.Skill;

import java.util.List;

public interface SkillDAO extends GenericDAO<Skill, Integer> {
    // Additional methods specific to Skill
    Skill findByName(String name) throws Exception;
    List<Skill> findByNameContaining(String keyword) throws Exception;
    boolean existsByName(String name) throws Exception;
    List<Skill> findSkillsByUserId(Integer userId) throws Exception;
    List<Skill> findSkillsByJobId(Integer jobId) throws Exception;
    List<Skill> findMostPopularSkills(int limit) throws Exception;
}