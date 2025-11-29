package org.smart_job.dao.impl;

import org.smart_job.dao.UserSkillDAO;
import org.smart_job.entity.UserSkill;
import org.smart_job.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserSkillDAOImpl implements UserSkillDAO {

    @Override
    public UserSkill insert(UserSkill entity) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            entity.markCreated();

            String sql = "INSERT INTO user_skills (user_id, skill_id, created_at, updated_at) VALUES (?, ?, ?, ?)";

            int affected = JdbcUtils.executeUpdate(conn, sql,
                    entity.getUserId(),
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
    public UserSkill update(UserSkill entity) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            entity.markUpdated();

            String sql = "UPDATE user_skills SET updated_at = ? WHERE user_id = ? AND skill_id = ?";

            int affected = JdbcUtils.executeUpdate(conn, sql,
                    entity.getUpdatedAt(),
                    entity.getUserId(),
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
        // For junction tables, we typically delete by composite key
        throw new UnsupportedOperationException("Use removeSkillFromUser(userId, skillId) instead");
    }

    @Override
    public UserSkill findById(Integer id) throws Exception {
        // Not applicable for junction tables with composite primary key
        throw new UnsupportedOperationException("Use userHasSkill(userId, skillId) instead");
    }

    @Override
    public List<UserSkill> findAll() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<UserSkill> userSkills = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM user_skills ORDER BY user_id, skill_id";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                userSkills.add(mapResultSetToUserSkill(rs));
            }

            return userSkills;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public boolean addSkillToUser(Integer userId, Integer skillId) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            // Check if relationship already exists
            if (userHasSkill(userId, skillId)) {
                return true; // Already exists
            }

            UserSkill userSkill = new UserSkill();
            userSkill.setUserId(userId);
            userSkill.setSkillId(skillId);
            userSkill.markCreated();

            String sql = "INSERT INTO user_skills (user_id, skill_id, created_at, updated_at) VALUES (?, ?, ?, ?)";

            int affected = JdbcUtils.executeUpdate(conn, sql,
                    userId, skillId, userSkill.getCreatedAt(), userSkill.getUpdatedAt()
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
    public boolean removeSkillFromUser(Integer userId, Integer skillId) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "DELETE FROM user_skills WHERE user_id = ? AND skill_id = ?";
            int affected = JdbcUtils.executeUpdate(conn, sql, userId, skillId);

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
    public boolean userHasSkill(Integer userId, Integer skillId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM user_skills WHERE user_id = ? AND skill_id = ?";
            rs = JdbcUtils.executeQuery(conn, sql, userId, skillId);

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
    public void addSkillsToUser(Integer userId, List<Integer> skillIds) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            for (Integer skillId : skillIds) {
                if (!userHasSkill(userId, skillId)) {
                    addSkillToUser(userId, skillId);
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
    public void removeAllSkillsFromUser(Integer userId) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "DELETE FROM user_skills WHERE user_id = ?";
            JdbcUtils.executeUpdate(conn, sql, userId);
            JdbcUtils.commit(conn);

        } catch (Exception e) {
            JdbcUtils.rollback(conn);
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public void replaceUserSkills(Integer userId, List<Integer> skillIds) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            // Remove all existing skills
            removeAllSkillsFromUser(userId);

            // Add new skills
            addSkillsToUser(userId, skillIds);

            JdbcUtils.commit(conn);

        } catch (Exception e) {
            JdbcUtils.rollback(conn);
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<UserSkill> findByUserId(Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<UserSkill> userSkills = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM user_skills WHERE user_id = ? ORDER BY skill_id";
            rs = JdbcUtils.executeQuery(conn, sql, userId);

            while (rs.next()) {
                userSkills.add(mapResultSetToUserSkill(rs));
            }

            return userSkills;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<UserSkill> findBySkillId(Integer skillId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<UserSkill> userSkills = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM user_skills WHERE skill_id = ? ORDER BY user_id";
            rs = JdbcUtils.executeQuery(conn, sql, skillId);

            while (rs.next()) {
                userSkills.add(mapResultSetToUserSkill(rs));
            }

            return userSkills;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Integer> findUserIdsBySkillId(Integer skillId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> userIds = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT user_id FROM user_skills WHERE skill_id = ? ORDER BY user_id";
            rs = JdbcUtils.executeQuery(conn, sql, skillId);

            while (rs.next()) {
                userIds.add(rs.getInt("user_id"));
            }

            return userIds;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Integer> findSkillIdsByUserId(Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> skillIds = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT skill_id FROM user_skills WHERE user_id = ? ORDER BY skill_id";
            rs = JdbcUtils.executeQuery(conn, sql, userId);

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
    public int countUsersBySkillId(Integer skillId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM user_skills WHERE skill_id = ?";
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
    public int countSkillsByUserId(Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM user_skills WHERE user_id = ?";
            rs = JdbcUtils.executeQuery(conn, sql, userId);

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
    public List<Object[]> getUserSkillCounts() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Object[]> results = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT user_id, COUNT(*) as skill_count FROM user_skills GROUP BY user_id ORDER BY skill_count DESC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                results.add(new Object[]{rs.getInt("user_id"), rs.getInt("skill_count")});
            }

            return results;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Object[]> getSkillPopularityStats() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Object[]> results = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT skill_id, COUNT(*) as user_count FROM user_skills GROUP BY skill_id ORDER BY user_count DESC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                results.add(new Object[]{rs.getInt("skill_id"), rs.getInt("user_count")});
            }

            return results;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Integer> findUsersWithSimilarSkills(Integer userId, int minCommonSkills) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> similarUsers = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT us2.user_id, COUNT(*) as common_skills " +
                    "FROM user_skills us1 " +
                    "INNER JOIN user_skills us2 ON us1.skill_id = us2.skill_id " +
                    "WHERE us1.user_id = ? AND us2.user_id != ? " +
                    "GROUP BY us2.user_id " +
                    "HAVING common_skills >= ? " +
                    "ORDER BY common_skills DESC";
            rs = JdbcUtils.executeQuery(conn, sql, userId, userId, minCommonSkills);

            while (rs.next()) {
                similarUsers.add(rs.getInt("user_id"));
            }

            return similarUsers;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Integer> findCommonSkillsBetweenUsers(Integer userId1, Integer userId2) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> commonSkills = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT us1.skill_id " +
                    "FROM user_skills us1 " +
                    "INNER JOIN user_skills us2 ON us1.skill_id = us2.skill_id " +
                    "WHERE us1.user_id = ? AND us2.user_id = ? " +
                    "ORDER BY us1.skill_id";
            rs = JdbcUtils.executeQuery(conn, sql, userId1, userId2);

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

    private UserSkill mapResultSetToUserSkill(ResultSet rs) throws SQLException {
        UserSkill userSkill = new UserSkill();

        userSkill.setUserId(rs.getInt("user_id"));
        userSkill.setSkillId(rs.getInt("skill_id"));

        if (rs.getTimestamp("created_at") != null) {
            userSkill.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            userSkill.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }

        return userSkill;
    }
}