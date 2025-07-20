package org.smart_job.dao.impl;

import org.smart_job.dao.UserDao;
import org.smart_job.entity.User;
import org.smart_job.ulties.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public User insert(User user) throws SQLException {
        String sql = " INSERT INTO users (user_name, last_name, email, password, image, country, dob, created_at, updated_at)   VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        user.markCreated();

        try (Connection conn = JdbcUtils.getConnection()) {
            int result =  JdbcUtils.executeInsert(conn, sql,
                    user.getUserName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getImage(),
                    user.getCountry(),
                    user.getDob() != null ? new Date(user.getDob().getTime()) : null,
                    java.sql.Timestamp.valueOf(user.getCreatedAt()),
                    java.sql.Timestamp.valueOf(user.getUpdatedAt())
            );
            JdbcUtils.commit(conn);
            user.setId(result);
            return user;
        }
    }

    @Override
    public User update(User user) throws SQLException {
        String sql = " UPDATE users   SET user_name=?, last_name=?, email=?, password=?, image=?, country=?, dob=?, updated_at=?  WHERE id=? ";

        user.markUpdated();

        try (Connection conn = JdbcUtils.getConnection()) {
           int result =  JdbcUtils.executeUpdate(conn, sql,
                    user.getUserName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getImage(),
                    user.getCountry(),
                    user.getDob() != null ? new Date(user.getDob().getTime()) : null,
                    java.sql.Timestamp.valueOf(user.getUpdatedAt()),
                    user.getId()
            );
            JdbcUtils.commit(conn);
            return user;
        }
    }

    @Override
    public int delete(Integer id) throws SQLException {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection conn = JdbcUtils.getConnection()) {
            int result = JdbcUtils.executeUpdate(conn, sql, id);
            JdbcUtils.commit(conn);
            return result;
        }
    }

    @Override
    public User findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id=?";
        try (Connection conn = JdbcUtils.getConnection();
             ResultSet rs = JdbcUtils.executeQuery(conn, sql, id)) {
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        }
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConnection();
             ResultSet rs = JdbcUtils.executeQuery(conn, sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUserName(rs.getString("user_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setImage(rs.getString("image"));
        user.setCountry(rs.getString("country"));
        user.setDob(rs.getDate("dob"));
        user.setCreatedAt(rs.getTimestamp("created_at") != null
                ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        user.setUpdatedAt(rs.getTimestamp("updated_at") != null
                ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        return user;
    }
}
