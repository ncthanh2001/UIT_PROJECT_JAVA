package org.smart_job.dao.impl;

import org.smart_job.dao.JobDAO;
import org.smart_job.entity.Job;
import org.smart_job.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class JobDAOImpl implements JobDAO {

    @Override
    public Job insert(Job entity) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            entity.markCreated();

            String sql = "INSERT INTO jobs (title, company_name, country, city, url, description, expiration_date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int generatedId = JdbcUtils.executeInsert(conn, sql,
                    entity.getTitle(),
                    entity.getCompanyName(),
                    entity.getCountry(),
                    entity.getCity(),
                    entity.getUrl(),
                    entity.getDescription(),
                    entity.getExpirationDate(),
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
            if (conn != null) conn.close();
        }
    }

    @Override
    public Job update(Job entity) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            entity.markUpdated();

            String sql = "UPDATE jobs SET title = ?, company_name = ?, country = ?, city = ?, url = ?, description = ?, expiration_date = ?, updated_at = ? WHERE id = ?";

            int affected = JdbcUtils.executeUpdate(conn, sql,
                    entity.getTitle(),
                    entity.getCompanyName(),
                    entity.getCountry(),
                    entity.getCity(),
                    entity.getUrl(),
                    entity.getDescription(),
                    entity.getExpirationDate(),
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
            if (conn != null) conn.close();
        }
    }

    @Override
    public int delete(Integer id) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "DELETE FROM jobs WHERE id = ?";
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
            if (conn != null) conn.close();
        }
    }

    @Override
    public Job findById(Integer id) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs WHERE id = ?";
            rs = JdbcUtils.executeQuery(conn, sql, id);

            if (rs.next()) {
                return mapResultSetToJob(rs);
            }

            return null;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findAll() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs ORDER BY created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findByCountry(String country) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs WHERE country = ? ORDER BY created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql, country);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findByCity(String city) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs WHERE city = ? ORDER BY created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql, city);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findByLocation(String country, String city) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs WHERE country = ? AND city = ? ORDER BY created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql, country, city);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findByCompanyName(String companyName) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs WHERE company_name = ? ORDER BY created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql, companyName);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findByCompanyNameContaining(String keyword) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs WHERE company_name LIKE ? ORDER BY created_at DESC";
            String searchPattern = "%" + keyword + "%";
            rs = JdbcUtils.executeQuery(conn, sql, searchPattern);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findByTitleContaining(String keyword) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs WHERE title LIKE ? ORDER BY created_at DESC";
            String searchPattern = "%" + keyword + "%";
            rs = JdbcUtils.executeQuery(conn, sql, searchPattern);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> searchInDescription(String keyword) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs WHERE description LIKE ? ORDER BY created_at DESC";
            String searchPattern = "%" + keyword + "%";
            rs = JdbcUtils.executeQuery(conn, sql, searchPattern);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> searchJobs(String keyword) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs WHERE title LIKE ? OR company_name LIKE ? OR description LIKE ? ORDER BY created_at DESC";
            String searchPattern = "%" + keyword + "%";
            rs = JdbcUtils.executeQuery(conn, sql, searchPattern, searchPattern, searchPattern);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findActiveJobs() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs WHERE expiration_date IS NULL OR expiration_date > NOW() ORDER BY created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findExpiredJobs() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs WHERE expiration_date IS NOT NULL AND expiration_date <= NOW() ORDER BY expiration_date DESC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findJobsExpiringBefore(LocalDateTime date) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs WHERE expiration_date IS NOT NULL AND expiration_date <= ? ORDER BY expiration_date ASC";
            rs = JdbcUtils.executeQuery(conn, sql, date);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findJobsBySkillId(Integer skillId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT j.* FROM jobs j " +
                    "INNER JOIN job_skills js ON j.id = js.job_id " +
                    "WHERE js.skill_id = ? ORDER BY j.created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql, skillId);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findJobsMatchingUserSkills(Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT DISTINCT j.*, COUNT(js.skill_id) as matching_skills " +
                    "FROM jobs j " +
                    "INNER JOIN job_skills js ON j.id = js.job_id " +
                    "INNER JOIN user_skills us ON js.skill_id = us.skill_id " +
                    "WHERE us.user_id = ? " +
                    "AND (j.expiration_date IS NULL OR j.expiration_date > NOW()) " +
                    "GROUP BY j.id " +
                    "ORDER BY matching_skills DESC, j.created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql, userId);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public int countByCompanyName(String companyName) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM jobs WHERE company_name = ?";
            rs = JdbcUtils.executeQuery(conn, sql, companyName);

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
    public List<String> getUniqueCompanies() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<String> companies = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT DISTINCT company_name FROM jobs WHERE company_name IS NOT NULL ORDER BY company_name ASC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                companies.add(rs.getString("company_name"));
            }

            return companies;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<String> getUniqueLocations() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<String> locations = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT DISTINCT CONCAT(COALESCE(city, ''), CASE WHEN city IS NOT NULL AND country IS NOT NULL THEN ', ' ELSE '' END, COALESCE(country, '')) as location " +
                    "FROM jobs WHERE (city IS NOT NULL OR country IS NOT NULL) ORDER BY location ASC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                String location = rs.getString("location");
                if (location != null && !location.trim().isEmpty()) {
                    locations.add(location);
                }
            }

            return locations;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Job> findRecentJobs(int limit) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM jobs ORDER BY created_at DESC LIMIT ?";
            rs = JdbcUtils.executeQuery(conn, sql, limit);

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }
    @Override
    public List<Job> findJobs(String keyword, String country, String city, int page, int pageSize) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        List<Job> jobs = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            // Build dynamic SQL
            StringBuilder sql = new StringBuilder("SELECT * FROM jobs WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                sql.append(" AND title LIKE ?");
                params.add("%" + keyword + "%");
            }

            if (country != null && !country.isEmpty()) {
                sql.append(" AND country = ?");
                params.add(country);
            }

            if (city != null && !city.isEmpty()) {
                sql.append(" AND city = ?");
                params.add(city);
            }

            sql.append(" ORDER BY created_at DESC LIMIT ? OFFSET ?");
            params.add(pageSize);
            params.add((page - 1) * pageSize);

            rs = JdbcUtils.executeQuery(conn, sql.toString(), params.toArray());

            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }

            return jobs;
        } finally {
            if (rs != null) rs.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public int countJobs(String keyword, String country, String city) throws Exception {
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM jobs WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                sql.append(" AND title LIKE ?");
                params.add("%" + keyword + "%");
            }

            if (country != null && !country.isEmpty()) {
                sql.append(" AND country = ?");
                params.add(country);
            }

            if (city != null && !city.isEmpty()) {
                sql.append(" AND city = ?");
                params.add(city);
            }

            rs = JdbcUtils.executeQuery(conn, sql.toString(), params.toArray());

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;
        } finally {
            if (rs != null) rs.close();
            if (conn != null) conn.close();
        }
    }

    private Job mapResultSetToJob(ResultSet rs) throws SQLException {
        Job job = new Job();

        job.setId(rs.getInt("id"));
        job.setTitle(rs.getString("title"));
        job.setCompanyName(rs.getString("company_name"));
        job.setCountry(rs.getString("country"));
        job.setCity(rs.getString("city"));
        job.setUrl(rs.getString("url"));
        job.setDescription(rs.getString("description"));

        if (rs.getTimestamp("expiration_date") != null) {
            job.setExpirationDate(rs.getTimestamp("expiration_date").toLocalDateTime());
        }
        if (rs.getTimestamp("created_at") != null) {
            job.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            job.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }

        return job;
    }
}
