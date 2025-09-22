package org.smart_job.util;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.stream.Collectors;

public class JdbcUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/uit_smart_job";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    ///
    /// - Dùng để khởi tạo Database
    ///
    /// - Sau khi chạy có thể comment
    ///
    /// Lưu ý: chỉ nên chạy 1 lần
    ///
    public static void initDB() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Load SQL file
            InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("sql/create_table.sql");
            if (is == null) {
                throw new RuntimeException("Cannot find create_table.sql");
            }

            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Chia các câu lệnh bằng dấu ;
            for (String s : sql.split(";")) {
                if (!s.trim().isEmpty()) {
                    stmt.execute(s);
                }
            }

            System.out.println("Database tables initialized!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // open connection
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        conn.setAutoCommit(false);
        return conn;
    }

    // commit transaction
    public static void commit(Connection conn) {
        if (conn != null) {
            try { conn.commit(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // rollback transaction
    public static void rollback(Connection conn) {
        if (conn != null) {
            try { conn.rollback(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // excute
    public static int executeUpdate(Connection conn, String sql, Object... params) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParameters(pstmt, params);
            return pstmt.executeUpdate();
        }
    }

    public static int executeInsert(Connection conn, String sql, Object... params) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(pstmt, params);
            int affected = pstmt.executeUpdate();
            if (affected == 0) return 0;

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // trả về ID mới
                }
            }
            return 0;
        }
    }
    // ultis excutequery
    public static ResultSet executeQuery(Connection conn, String sql, Object... params) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        setParameters(pstmt, params);
        return pstmt.executeQuery(); // bạn phải tự đóng pstmt và rs
    }

    // exute statement
    public static int executeStatementUpdate(Connection conn, String sql) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    // biding param
    private static void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }
}
