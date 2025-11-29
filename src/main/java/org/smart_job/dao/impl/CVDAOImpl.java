package org.smart_job.dao.impl;

import org.smart_job.dao.CVDao;
import org.smart_job.entity.CV;
import org.smart_job.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CVDAOImpl implements CVDao {

    @Override
    public CV insert(CV entity) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            // Mark entity as created
            entity.markCreated();

            String sql = "INSERT INTO cvs (user_id, file_path, parsed_text, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";

            int generatedId = JdbcUtils.executeInsert(conn, sql,
                    entity.getUserId(),
                    entity.getFilePath(),
                    entity.getParsedText(),
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
    public CV update(CV entity) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            // Mark entity as updated
            entity.markUpdated();

            String sql = "UPDATE cvs SET user_id = ?, file_path = ?, parsed_text = ?, updated_at = ? WHERE id = ?";

            int affected = JdbcUtils.executeUpdate(conn, sql,
                    entity.getUserId(),
                    entity.getFilePath(),
                    entity.getParsedText(),
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

            String sql = "DELETE FROM cvs WHERE id = ?";
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
    public CV findById(Integer id) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM cvs WHERE id = ?";
            rs = JdbcUtils.executeQuery(conn, sql, id);

            if (rs.next()) {
                return mapResultSetToCV(rs);
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
    public List<CV> findAll() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CV> cvs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM cvs ORDER BY created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                cvs.add(mapResultSetToCV(rs));
            }

            return cvs;

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<CV> findByUserId(Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CV> cvs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM cvs WHERE user_id = ? ORDER BY created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql, userId);

            while (rs.next()) {
                cvs.add(mapResultSetToCV(rs));
            }

            return cvs;

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public CV findLatestByUserId(Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM cvs WHERE user_id = ? ORDER BY created_at DESC LIMIT 1";
            rs = JdbcUtils.executeQuery(conn, sql, userId);

            if (rs.next()) {
                return mapResultSetToCV(rs);
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
    public List<CV> findByFilePathContaining(String keyword) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CV> cvs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM cvs WHERE file_path LIKE ? ORDER BY created_at DESC";
            String searchPattern = "%" + keyword + "%";
            rs = JdbcUtils.executeQuery(conn, sql, searchPattern);

            while (rs.next()) {
                cvs.add(mapResultSetToCV(rs));
            }

            return cvs;

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public boolean existsByFilePath(String filePath) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM cvs WHERE file_path = ?";
            rs = JdbcUtils.executeQuery(conn, sql, filePath);

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
    public List<CV> findByUserIdOrderByCreatedAt(Integer userId, boolean ascending) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CV> cvs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String orderDirection = ascending ? "ASC" : "DESC";
            String sql = "SELECT * FROM cvs WHERE user_id = ? ORDER BY created_at " + orderDirection;
            rs = JdbcUtils.executeQuery(conn, sql, userId);

            while (rs.next()) {
                cvs.add(mapResultSetToCV(rs));
            }

            return cvs;

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public int countByUserId(Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM cvs WHERE user_id = ?";
            rs = JdbcUtils.executeQuery(conn, sql, userId);

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<CV> searchInParsedText(String keyword) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CV> cvs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM cvs WHERE parsed_text LIKE ? ORDER BY created_at DESC";
            String searchPattern = "%" + keyword + "%";
            rs = JdbcUtils.executeQuery(conn, sql, searchPattern);

            while (rs.next()) {
                cvs.add(mapResultSetToCV(rs));
            }

            return cvs;

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public void deleteByUserId(Integer userId) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "DELETE FROM cvs WHERE user_id = ?";
            JdbcUtils.executeUpdate(conn, sql, userId);
            JdbcUtils.commit(conn);

        } catch (Exception e) {
            JdbcUtils.rollback(conn);
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Helper method to map ResultSet to CV entity
     */
    private CV mapResultSetToCV(ResultSet rs) throws SQLException {
        CV cv = new CV();

        cv.setId(rs.getInt("id"));
        cv.setUserId(rs.getInt("user_id"));
        cv.setFilePath(rs.getString("file_path"));
        cv.setParsedText(rs.getString("parsed_text"));

        // Handle LocalDateTime conversion
        if (rs.getTimestamp("created_at") != null) {
            cv.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            cv.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }

        return cv;
    }
}