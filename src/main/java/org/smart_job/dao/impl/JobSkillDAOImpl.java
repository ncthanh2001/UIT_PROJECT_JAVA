package org.smart_job.dao.impl;

import org.smart_job.dao.JobSkillDAO;
import org.smart_job.entity.JobSkill;
import org.smart_job.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobSkillDAOImpl implements JobSkillDAO {

    @Override
    public JobSkill insert(JobSkill entity) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            entity.markCreated();

            String sql = "INSERT INTO job_skills (job_id, skill_id, created_at, updated_at) VALUES (?, ?, ?, ?)";

            int affected = JdbcUtils.executeUpdate(conn, sql,
                    entity.getJobId(),
                    entity.getSkillId(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt()
            );

            if (affected > 0) {
                JdbcUtils.commit(conn);
                return entity;
            } else {
                JdbcUtils.rollback(conn);
                return null;
            }

        } catch (Exception e) {
            JdbcUtils.rollback(conn);
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public JobSkill update(JobSkill entity) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            entity.markUpdated();

            String sql = "UPDATE job_skills SET updated_at = ? WHERE job_id = ? AND skill_id = ?";

            int affected = JdbcUtils.executeUpdate(conn, sql,
                    entity.getUpdatedAt(),
                    entity.getJobId(),
                    entity.getSkillId()
            );

            if (affected > 0) {
                JdbcUtils.commit(conn);
                return entity;
            } else {
                JdbcUtils.rollback(conn);
                return null;
            }

        } catch (Exception e) {
            JdbcUtils.rollback(conn);
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public int delete(Integer id) throws Exception {
        throw new UnsupportedOperationException("Use removeSkillFromJob(jobId, skillId) instead");
    }

    @Override
    public JobSkill findById(Integer id) throws Exception {
        throw new UnsupportedOperationException("Use jobRequiresSkill(jobId, skillId) instead");
    }

    @Override
    public List<JobSkill> findAll() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobSkill> jobSkills = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_skills ORDER BY job_id, skill_id";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                jobSkills.add(mapResultSetToJobSkill(rs));
            }

            return jobSkills;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public boolean addSkillToJob(Integer jobId, Integer skillId) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            if (jobRequiresSkill(jobId, skillId)) {
                return true; // Already exists
            }

            JobSkill jobSkill = new JobSkill();
            jobSkill.setJobId(jobId);
            jobSkill.setSkillId(skillId);
            jobSkill.markCreated();

            String sql = "INSERT INTO job_skills (job_id, skill_id, created_at, updated_at) VALUES (?, ?, ?, ?)";

            int affected = JdbcUtils.executeUpdate(conn, sql,
                    jobId, skillId, jobSkill.getCreatedAt(), jobSkill.getUpdatedAt()
            );

            if (affected > 0) {
                JdbcUtils.commit(conn);
                return true;
            } else {
                JdbcUtils.rollback(conn);
                return false;
            }

        } catch (Exception e) {
            JdbcUtils.rollback(conn);
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public boolean removeSkillFromJob(Integer jobId, Integer skillId) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "DELETE FROM job_skills WHERE job_id = ? AND skill_id = ?";
            int affected = JdbcUtils.executeUpdate(conn, sql, jobId, skillId);

            if (affected > 0) {
                JdbcUtils.commit(conn);
                return true;
            } else {
                JdbcUtils.rollback(conn);
                return false;
            }

        } catch (Exception e) {
            JdbcUtils.rollback(conn);
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public boolean jobRequiresSkill(Integer jobId, Integer skillId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM job_skills WHERE job_id = ? AND skill_id = ?";
            rs = JdbcUtils.executeQuery(conn, sql, jobId, skillId);

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            return false;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public void addSkillsToJob(Integer jobId, List<Integer> skillIds) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            for (Integer skillId : skillIds) {
                if (!jobRequiresSkill(jobId, skillId)) {
                    addSkillToJob(jobId, skillId);
                }
            }

            JdbcUtils.commit(conn);

        } catch (Exception e) {
            JdbcUtils.rollback(conn);
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public void removeAllSkillsFromJob(Integer jobId) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "DELETE FROM job_skills WHERE job_id = ?";
            JdbcUtils.executeUpdate(conn, sql, jobId);
            JdbcUtils.commit(conn);

        } catch (Exception e) {
            JdbcUtils.rollback(conn);
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public void replaceJobSkills(Integer jobId, List<Integer> skillIds) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            removeAllSkillsFromJob(jobId);
            addSkillsToJob(jobId, skillIds);

            JdbcUtils.commit(conn);

        } catch (Exception e) {
            JdbcUtils.rollback(conn);
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobSkill> findByJobId(Integer jobId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobSkill> jobSkills = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_skills WHERE job_id = ? ORDER BY skill_id";
            rs = JdbcUtils.executeQuery(conn, sql, jobId);

            while (rs.next()) {
                jobSkills.add(mapResultSetToJobSkill(rs));
            }

            return jobSkills;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobSkill> findBySkillId(Integer skillId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobSkill> jobSkills = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_skills WHERE skill_id = ? ORDER BY job_id";
            rs = JdbcUtils.executeQuery(conn, sql, skillId);

            while (rs.next()) {
                jobSkills.add(mapResultSetToJobSkill(rs));
            }

            return jobSkills;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Integer> findJobIdsBySkillId(Integer skillId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> jobIds = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT job_id FROM job_skills WHERE skill_id = ? ORDER BY job_id";
            rs = JdbcUtils.executeQuery(conn, sql, skillId);

            while (rs.next()) {
                jobIds.add(rs.getInt("job_id"));
            }

            return jobIds;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Integer> findSkillIdsByJobId(Integer jobId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> skillIds = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT skill_id FROM job_skills WHERE job_id = ? ORDER BY skill_id";
            rs = JdbcUtils.executeQuery(conn, sql, jobId);

            while (rs.next()) {
                skillIds.add(rs.getInt("skill_id"));
            }

            return skillIds;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public int countJobsBySkillId(Integer skillId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM job_skills WHERE skill_id = ?";
            rs = JdbcUtils.executeQuery(conn, sql, skillId);

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public int countSkillsByJobId(Integer jobId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM job_skills WHERE job_id = ?";
            rs = JdbcUtils.executeQuery(conn, sql, jobId);

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Object[]> getJobSkillCounts() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Object[]> results = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT job_id, COUNT(*) as skill_count FROM job_skills GROUP BY job_id ORDER BY skill_count DESC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                results.add(new Object[]{rs.getInt("job_id"), rs.getInt("skill_count")});
            }

            return results;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Object[]> getSkillDemandStats() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Object[]> results = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT skill_id, COUNT(*) as job_count FROM job_skills GROUP BY skill_id ORDER BY job_count DESC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                results.add(new Object[]{rs.getInt("skill_id"), rs.getInt("job_count")});
            }

            return results;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Integer> findJobsWithSimilarSkills(Integer jobId, int minCommonSkills) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> similarJobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT js2.job_id, COUNT(*) as common_skills " +
                    "FROM job_skills js1 " +
                    "INNER JOIN job_skills js2 ON js1.skill_id = js2.skill_id " +
                    "WHERE js1.job_id = ? AND js2.job_id != ? " +
                    "GROUP BY js2.job_id " +
                    "HAVING common_skills >= ? " +
                    "ORDER BY common_skills DESC";
            rs = JdbcUtils.executeQuery(conn, sql, jobId, jobId, minCommonSkills);

            while (rs.next()) {
                similarJobs.add(rs.getInt("job_id"));
            }

            return similarJobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Integer> findCommonSkillsBetweenJobs(Integer jobId1, Integer jobId2) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> commonSkills = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT js1.skill_id " +
                    "FROM job_skills js1 " +
                    "INNER JOIN job_skills js2 ON js1.skill_id = js2.skill_id " +
                    "WHERE js1.job_id = ? AND js2.job_id = ? " +
                    "ORDER BY js1.skill_id";
            rs = JdbcUtils.executeQuery(conn, sql, jobId1, jobId2);

            while (rs.next()) {
                commonSkills.add(rs.getInt("skill_id"));
            }

            return commonSkills;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Integer> findJobsMatchingSkills(List<Integer> skillIds, int minMatchCount) throws Exception {
        if (skillIds == null || skillIds.isEmpty()) {
            return new ArrayList<>();
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> matchingJobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            StringBuilder sql = new StringBuilder("SELECT job_id, COUNT(*) as matching_skills " +
                    "FROM job_skills WHERE skill_id IN (");
            for (int i = 0; i < skillIds.size(); i++) {
                if (i > 0) sql.append(", ");
                sql.append("?");
            }
            sql.append(") GROUP BY job_id HAVING matching_skills >= ? ORDER BY matching_skills DESC");

            Object[] params = new Object[skillIds.size() + 1];
            for (int i = 0; i < skillIds.size(); i++) {
                params[i] = skillIds.get(i);
            }
            params[skillIds.size()] = minMatchCount;

            rs = JdbcUtils.executeQuery(conn, sql.toString(), params);

            while (rs.next()) {
                matchingJobs.add(rs.getInt("job_id"));
            }

            return matchingJobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * Helper method to map ResultSet to JobSkill entity
     */
    private JobSkill mapResultSetToJobSkill(ResultSet rs) throws SQLException {
        JobSkill jobSkill = new JobSkill();

        jobSkill.setJobId(rs.getInt("job_id"));
        jobSkill.setSkillId(rs.getInt("skill_id"));

        if (rs.getTimestamp("created_at") != null) {
            jobSkill.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            jobSkill.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }

        return jobSkill;
    }
}