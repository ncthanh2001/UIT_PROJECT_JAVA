package org.smart_job.service.impl;

import org.smart_job.dao.SkillDAO;
import org.smart_job.dao.impl.SkillDAOImpl;
import org.smart_job.entity.Skill;
import org.smart_job.service.SkillService;

import java.util.List;

public class SkillServiceImpl implements SkillService {
    private final SkillDAO skillDAO = new SkillDAOImpl();

    @Override
    public Skill getSkillById(Integer id) throws Exception {
        return skillDAO.findById(id);
    }

    @Override
    public Skill getSkillByName(String name) throws Exception {
        return skillDAO.findByName(name);
    }

    @Override
    public List<Skill> searchSkills(String keyword) throws Exception {
        return skillDAO.findByNameContaining(keyword);
    }

    @Override
    public boolean existsByName(String name) throws Exception {
        return skillDAO.existsByName(name);
    }

    @Override
    public List<Skill> getSkillsByUserId(Integer userId) throws Exception {
        return skillDAO.findSkillsByUserId(userId);
    }

    @Override
    public List<Skill> getSkillsByJobId(Integer jobId) throws Exception {
        return skillDAO.findSkillsByJobId(jobId);
    }

    @Override
    public List<Skill> getMostPopularSkills(int limit) throws Exception {
        return skillDAO.findMostPopularSkills(limit);
    }

    @Override
    public Skill createSkill(Skill skill) throws Exception {
        return skillDAO.insert(skill);
    }

    @Override
    public void updateSkill(Skill skill) throws Exception {
        skillDAO.update(skill);
    }

    @Override
    public void deleteSkill(Integer id) throws Exception {
        skillDAO.delete(id);
    }

    @Override
    public List<Skill> getAllSkills() throws Exception {
        return skillDAO.findAll();
    }
}
