package org.smart_job.dao.impl;

import org.smart_job.dao.SkillDAO;
import org.smart_job.entity.Skill;
import org.smart_job.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillDAOImpl implements SkillDAO {

    @Override
    public Skill insert(Skill entity) throws Exception {
            Connection conn = null;
            try {
                conn = JdbcUtils.getConnection();

                // Mark entity as created
                entity.markCreated();

                String sql = "INSERT INTO skills (name, created_at, updated_at) VALUES (?, ?, ?)";

                int generatedId = JdbcUtils.executeInsert(conn, sql,
                        entity.getName(),
                        entity.getCreatedAt(),
                        entity.getUpdatedAt()
                );

                if (generatedId > 0) {
                    entity.setId(generatedId);
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
                if (conn != null) {
                    conn.close();
                }
            }
        }

        @Override
        public Skill update(Skill entity) throws Exception {
            Connection conn = null;
            try {
                conn = JdbcUtils.getConnection();

                // Mark entity as updated
                entity.markUpdated();

                String sql = "UPDATE skills SET name = ?, updated_at = ? WHERE id = ?";

                int affected = JdbcUtils.executeUpdate(conn, sql,
                        entity.getName(),
                        entity.getUpdatedAt(),
                        entity.getId()
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
                if (conn != null) {
                    conn.close();
                }
            }
        }

        @Override
        public int delete(Integer id) throws Exception {
            Connection conn = null;
            try {
                conn = JdbcUtils.getConnection();

                String sql = "DELETE FROM skills WHERE id = ?";
                int affected = JdbcUtils.executeUpdate(conn, sql, id);

                if (affected > 0) {
                    JdbcUtils.commit(conn);
                } else {
                    JdbcUtils.rollback(conn);
                }

                return affected;

            } catch (Exception e) {
                JdbcUtils.rollback(conn);
                throw e;
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        }

        @Override
        public Skill findById(Integer id) throws Exception {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                conn = JdbcUtils.getConnection();

                String sql = "SELECT * FROM skills WHERE id = ?";
                rs = JdbcUtils.executeQuery(conn, sql, id);

                if (rs.next()) {
                    return mapResultSetToSkill(rs);
                }

                return null;

            } catch (Exception e) {
                throw e;
            } finally {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            }
        }

        @Override
        public List<Skill> findAll() throws Exception {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            List<Skill> skills = new ArrayList<>();

            try {
                conn = JdbcUtils.getConnection();

                String sql = "SELECT * FROM skills ORDER BY name ASC";
                rs = JdbcUtils.executeQuery(conn, sql);

                while (rs.next()) {
                    skills.add(mapResultSetToSkill(rs));
                }

                return skills;

            } catch (Exception e) {
                throw e;
            } finally {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            }
        }

        @Override
        public Skill findByName(String name) throws Exception {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                conn = JdbcUtils.getConnection();

                String sql = "SELECT * FROM skills WHERE name = ?";
                rs = JdbcUtils.executeQuery(conn, sql, name);

                if (rs.next()) {
                    return mapResultSetToSkill(rs);
                }

                return null;

            } catch (Exception e) {
                throw e;
            } finally {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            }
        }

        @Override
        public List<Skill> findByNameContaining(String keyword) throws Exception {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            List<Skill> skills = new ArrayList<>();

            try {
                conn = JdbcUtils.getConnection();

                String sql = "SELECT * FROM skills WHERE name LIKE ? ORDER BY name ASC";
                String searchPattern = "%" + keyword + "%";
                rs = JdbcUtils.executeQuery(conn, sql, searchPattern);

                while (rs.next()) {
                    skills.add(mapResultSetToSkill(rs));
                }

                return skills;

            } catch (Exception e) {
                throw e;
            } finally {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            }
        }

        @Override
        public boolean existsByName(String name) throws Exception {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                conn = JdbcUtils.getConnection();

                String sql = "SELECT COUNT(*) FROM skills WHERE name = ?";
                rs = JdbcUtils.executeQuery(conn, sql, name);

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }

                return false;

            } catch (Exception e) {
                throw e;
            } finally {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            }
        }

        @Override
        public List<Skill> findSkillsByUserId(Integer userId) throws Exception {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            List<Skill> skills = new ArrayList<>();

            try {
                conn = JdbcUtils.getConnection();

                String sql = "SELECT s.* FROM skills s " +
                        "INNER JOIN user_skills us ON s.id = us.skill_id " +
                        "WHERE us.user_id = ? ORDER BY s.name ASC";
                rs = JdbcUtils.executeQuery(conn, sql, userId);

                while (rs.next()) {
                    skills.add(mapResultSetToSkill(rs));
                }

                return skills;

            } catch (Exception e) {
                throw e;
            } finally {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            }
        }

        @Override
        public List<Skill> findSkillsByJobId(Integer jobId) throws Exception {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            List<Skill> skills = new ArrayList<>();

            try {
                conn = JdbcUtils.getConnection();

                String sql = "SELECT s.* FROM skills s " +
                        "INNER JOIN job_skills js ON s.id = js.skill_id " +
                        "WHERE js.job_id = ? ORDER BY s.name ASC";
                rs = JdbcUtils.executeQuery(conn, sql, jobId);

                while (rs.next()) {
                    skills.add(mapResultSetToSkill(rs));
                }

                return skills;

            } catch (Exception e) {
                throw e;
            } finally {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            }
        }

        @Override
        public List<Skill> findMostPopularSkills(int limit) throws Exception {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            List<Skill> skills = new ArrayList<>();

            try {
                conn = JdbcUtils.getConnection();

                String sql = "SELECT s.*, " +
                        "(SELECT COUNT(*) FROM user_skills us WHERE us.skill_id = s.id) + " +
                        "(SELECT COUNT(*) FROM job_skills js WHERE js.skill_id = s.id) as popularity " +
                        "FROM skills s " +
                        "ORDER BY popularity DESC " +
                        "LIMIT ?";
                rs = JdbcUtils.executeQuery(conn, sql, limit);

                while (rs.next()) {
                    skills.add(mapResultSetToSkill(rs));
                }

                return skills;

            } catch (Exception e) {
                throw e;
            } finally {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            }
        }

        /**
         * Helper method to map ResultSet to Skill entity
         */
        private Skill mapResultSetToSkill(ResultSet rs) throws SQLException {
            Skill skill = new Skill();

            skill.setId(rs.getInt("id"));
            skill.setName(rs.getString("name"));

            // Handle LocalDateTime conversion
            if (rs.getTimestamp("created_at") != null) {
                skill.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
            if (rs.getTimestamp("updated_at") != null) {
                skill.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }

            return skill;
        }
}
