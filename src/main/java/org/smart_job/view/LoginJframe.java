package org.smart_job.view;

import org.smart_job.ulties.JdbcUtils;

import javax.swing.*;
import java.sql.Connection;

public class LoginJframe {
    private JButton btn_login;
    private JPanel panel1;

    public LoginJframe() {
        // Check the db connection when init form interface
        try {
            Connection conn = JdbcUtils.getConnection();
            JOptionPane.showMessageDialog(null, "Connected to database successfully!");
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection failed: " + e.getMessage());
        }
    }

    public static void main(String[] args){

        JFrame frame = new JFrame("App");
        frame.setContentPane(new LoginJframe().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
