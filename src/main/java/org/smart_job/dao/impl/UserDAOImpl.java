package org.smart_job.dao.impl;

import org.smart_job.dao.UserDAO;
import org.smart_job.entity.User;
import org.smart_job.util.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public User insert(User entity) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            // Mark entity as created
            entity.markCreated();

            String sql = "INSERT INTO users (first_name, last_name, email, password, avatar, country, dob, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int generatedId = JdbcUtils.executeInsert(conn, sql,
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail(),
                    entity.getPassword(),
                    entity.getAvatar(),
                    entity.getCountry(),
                    entity.getDob(),
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
    public User update(User entity) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            // Mark entity as updated
            entity.markUpdated();

            String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, avatar = ?, country = ?, dob = ?, updated_at = ? WHERE id = ?";

            int affected = JdbcUtils.executeUpdate(conn, sql,
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail(),
                    entity.getPassword(),
                    entity.getAvatar(),
                    entity.getCountry(),
                    entity.getDob(),
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

            String sql = "DELETE FROM users WHERE id = ?";
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
    public User findById(Integer id) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM users WHERE id = ?";
            rs = JdbcUtils.executeQuery(conn, sql, id);

            if (rs.next()) {
                return mapResultSetToUser(rs);
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
    public List<User> findAll() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM users ORDER BY created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

            return users;

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public User findByEmail(String email) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM users WHERE email = ?";
            rs = JdbcUtils.executeQuery(conn, sql, email);

            if (rs.next()) {
                return mapResultSetToUser(rs);
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
    public List<User> findByCountry(String country) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM users WHERE country = ? ORDER BY created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql, country);

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

            return users;

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public boolean existsByEmail(String email) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
            rs = JdbcUtils.executeQuery(conn, sql, email);

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
    public List<User> searchByName(String name) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM users WHERE CONCAT(first_name, ' ', last_name) LIKE ? OR first_name LIKE ? OR last_name LIKE ? ORDER BY created_at DESC";
            String searchPattern = "%" + name + "%";
            rs = JdbcUtils.executeQuery(conn, sql, searchPattern, searchPattern, searchPattern);

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

            return users;

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * Helper method to map ResultSet to User entity
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setAvatar(rs.getString("avatar"));
        user.setCountry(rs.getString("country"));

        // Handle LocalDate conversion
        if (rs.getDate("dob") != null) {
            user.setDob(rs.getDate("dob").toLocalDate());
        }

        // Handle LocalDateTime conversion
        if (rs.getTimestamp("created_at") != null) {
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }

        return user;
    }
}
