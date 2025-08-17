package org.smart_job.dao.impl;

import org.smart_job.dao.JobApplicationDAO;
import org.smart_job.entity.JobApplication;
import org.smart_job.entity.JobStatus;
import org.smart_job.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class JobApplicationDAOImpl implements JobApplicationDAO {

    @Override
    public JobApplication insert(JobApplication entity) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            entity.markCreated();

            String sql = "INSERT INTO job_application (user_id, job_id, custom_title, custom_company, custom_description, custom_url, application_date, status, notes, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int generatedId = JdbcUtils.executeInsert(conn, sql,
                    entity.getUserId(),
                    entity.getJobId(),
                    entity.getCustomTitle(),
                    entity.getCustomCompany(),
                    entity.getCustomDescription(),
                    entity.getCustomUrl(),
                    entity.getApplicationDate(),
                    entity.getStatus().name().toLowerCase(),
                    entity.getNotes(),
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
    public JobApplication update(JobApplication entity) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            entity.markUpdated();

            String sql = "UPDATE job_application SET user_id = ?, job_id = ?, custom_title = ?, custom_company = ?, custom_description = ?, custom_url = ?, application_date = ?, status = ?, notes = ?, updated_at = ? WHERE id = ?";

            int affected = JdbcUtils.executeUpdate(conn, sql,
                    entity.getUserId(),
                    entity.getJobId(),
                    entity.getCustomTitle(),
                    entity.getCustomCompany(),
                    entity.getCustomDescription(),
                    entity.getCustomUrl(),
                    entity.getApplicationDate(),
                    entity.getStatus().name().toLowerCase(),
                    entity.getNotes(),
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

            String sql = "DELETE FROM job_application WHERE id = ?";
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
    public JobApplication findById(Integer id) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_application WHERE id = ?";
            rs = JdbcUtils.executeQuery(conn, sql, id);

            if (rs.next()) {
                return mapResultSetToJobApplication(rs);
            }

            return null;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findAll() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_application ORDER BY created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findByUserId(Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_application WHERE user_id = ? ORDER BY application_date DESC, created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql, userId);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findByUserIdAndStatus(Integer userId, JobStatus status) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_application WHERE user_id = ? AND status = ? ORDER BY application_date DESC, created_at DESC";
            rs = JdbcUtils.executeQuery(conn, sql, userId, status.name().toLowerCase());

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findByUserIdOrderByApplicationDate(Integer userId, boolean ascending) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String orderDirection = ascending ? "ASC" : "DESC";
            String sql = "SELECT * FROM job_application WHERE user_id = ? ORDER BY application_date " + orderDirection + ", created_at " + orderDirection;
            rs = JdbcUtils.executeQuery(conn, sql, userId);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findByJobId(Integer jobId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_application WHERE job_id = ? ORDER BY application_date DESC";
            rs = JdbcUtils.executeQuery(conn, sql, jobId);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public int countApplicationsByJobId(Integer jobId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM job_application WHERE job_id = ?";
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
    public List<JobApplication> findByStatus(JobStatus status) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_application WHERE status = ? ORDER BY application_date DESC";
            rs = JdbcUtils.executeQuery(conn, sql, status.name().toLowerCase());

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findApplicationsInStatuses(List<JobStatus> statuses) throws Exception {
        if (statuses == null || statuses.isEmpty()) {
            return new ArrayList<>();
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            StringBuilder sql = new StringBuilder("SELECT * FROM job_application WHERE status IN (");
            for (int i = 0; i < statuses.size(); i++) {
                if (i > 0) sql.append(", ");
                sql.append("?");
            }
            sql.append(") ORDER BY application_date DESC");

            Object[] params = statuses.stream()
                    .map(status -> status.name().toLowerCase())
                    .toArray();

            rs = JdbcUtils.executeQuery(conn, sql.toString(), params);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findApplicationsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_application WHERE application_date BETWEEN ? AND ? ORDER BY application_date DESC";
            rs = JdbcUtils.executeQuery(conn, sql, startDate, endDate);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findRecentApplications(int limit) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_application ORDER BY application_date DESC, created_at DESC LIMIT ?";
            rs = JdbcUtils.executeQuery(conn, sql, limit);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findApplicationsAppliedToday() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_application WHERE DATE(application_date) = CURDATE() ORDER BY application_date DESC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findCustomApplicationsByUserId(Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_application WHERE user_id = ? AND job_id IS NULL ORDER BY application_date DESC";
            rs = JdbcUtils.executeQuery(conn, sql, userId);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findJobPostingApplicationsByUserId(Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT * FROM job_application WHERE user_id = ? AND job_id IS NOT NULL ORDER BY application_date DESC";
            rs = JdbcUtils.executeQuery(conn, sql, userId);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public Map<JobStatus, Integer> getApplicationStatusCounts(Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<JobStatus, Integer> statusCounts = new HashMap<>();

        // Initialize all statuses with 0
        for (JobStatus status : JobStatus.values()) {
            statusCounts.put(status, 0);
        }

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT status, COUNT(*) as count FROM job_application WHERE user_id = ? GROUP BY status";
            rs = JdbcUtils.executeQuery(conn, sql, userId);

            while (rs.next()) {
                String statusStr = rs.getString("status");
                int count = rs.getInt("count");
                try {
                    JobStatus status = JobStatus.valueOf(statusStr.toUpperCase());
                    statusCounts.put(status, count);
                } catch (IllegalArgumentException e) {
                    // Skip unknown statuses
                }
            }

            return statusCounts;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findApplicationsNeedingFollowUp() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            // Applications that are in APPLIED or INTERVIEW status and haven't been updated in 7 days
            String sql = "SELECT * FROM job_application " +
                    "WHERE status IN ('applied', 'interview') " +
                    "AND updated_at < DATE_SUB(NOW(), INTERVAL 7 DAY) " +
                    "ORDER BY updated_at ASC";
            rs = JdbcUtils.executeQuery(conn, sql);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> findByCompanyName(String companyName, Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT ja.* FROM job_application ja " +
                    "LEFT JOIN jobs j ON ja.job_id = j.id " +
                    "WHERE ja.user_id = ? " +
                    "AND (j.company_name = ? OR ja.custom_company = ?) " +
                    "ORDER BY ja.application_date DESC";
            rs = JdbcUtils.executeQuery(conn, sql, userId, companyName, companyName);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<JobApplication> searchByCompanyOrTitle(String keyword, Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<JobApplication> applications = new ArrayList<>();

        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT ja.* FROM job_application ja " +
                    "LEFT JOIN jobs j ON ja.job_id = j.id " +
                    "WHERE ja.user_id = ? " +
                    "AND (j.company_name LIKE ? OR ja.custom_company LIKE ? " +
                    "OR j.title LIKE ? OR ja.custom_title LIKE ?) " +
                    "ORDER BY ja.application_date DESC";

            String searchPattern = "%" + keyword + "%";
            rs = JdbcUtils.executeQuery(conn, sql, userId, searchPattern, searchPattern, searchPattern, searchPattern);

            while (rs.next()) {
                applications.add(mapResultSetToJobApplication(rs));
            }

            return applications;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public boolean updateApplicationStatus(Integer applicationId, JobStatus newStatus) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "UPDATE job_application SET status = ?, updated_at = ? WHERE id = ?";
            int affected = JdbcUtils.executeUpdate(conn, sql,
                    newStatus.name().toLowerCase(),
                    LocalDateTime.now(),
                    applicationId);

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
    public boolean updateApplicationNotes(Integer applicationId, String notes) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "UPDATE job_application SET notes = ?, updated_at = ? WHERE id = ?";
            int affected = JdbcUtils.executeUpdate(conn, sql,
                    notes,
                    LocalDateTime.now(),
                    applicationId);

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

    /**
     * Helper method to map ResultSet to JobApplication entity
     */
    private JobApplication mapResultSetToJobApplication(ResultSet rs) throws SQLException {
        JobApplication application = new JobApplication();

        application.setId(rs.getInt("id"));
        application.setUserId(rs.getInt("user_id"));

        // Handle nullable job_id
        int jobId = rs.getInt("job_id");
        if (!rs.wasNull()) {
            application.setJobId(jobId);
        }

        application.setCustomTitle(rs.getString("custom_title"));
        application.setCustomCompany(rs.getString("custom_company"));
        application.setCustomDescription(rs.getString("custom_description"));
        application.setCustomUrl(rs.getString("custom_url"));
        application.setNotes(rs.getString("notes"));

        // Handle LocalDateTime conversion
        if (rs.getTimestamp("application_date") != null) {
            application.setApplicationDate(rs.getTimestamp("application_date").toLocalDateTime());
        }
        if (rs.getTimestamp("created_at") != null) {
            application.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            application.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }

        // Handle JobStatus enum
        String statusStr = rs.getString("status");
        if (statusStr != null) {
            try {
                application.setStatus(JobStatus.valueOf(statusStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                application.setStatus(JobStatus.WISH_LIST); // Default status
            }
        } else {
            application.setStatus(JobStatus.WISH_LIST);
        }

        return application;
    }
}