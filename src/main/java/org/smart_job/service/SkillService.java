package org.smart_job.service;

import org.smart_job.entity.Skill;

import java.util.List;

public interface SkillService {
    Skill getSkillById(Integer id) throws Exception;
    Skill getSkillByName(String name) throws Exception;
    List<Skill> searchSkills(String keyword) throws Exception;
    boolean existsByName(String name) throws Exception;
    List<Skill> getSkillsByUserId(Integer userId) throws Exception;
    List<Skill> getSkillsByJobId(Integer jobId) throws Exception;
    List<Skill> getMostPopularSkills(int limit) throws Exception;

    Skill createSkill(Skill skill) throws Exception;
    void updateSkill(Skill skill) throws Exception;
    void deleteSkill(Integer id) throws Exception;
    List<Skill> getAllSkills() throws Exception;
}
