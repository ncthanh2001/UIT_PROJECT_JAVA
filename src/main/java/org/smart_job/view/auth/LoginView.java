package org.smart_job.view.auth;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton; // Navigate to Register

    public LoginView() {
        setTitle("Login");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Email
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        emailField.setText("duong@gmail.com");
        gbc.gridx = 1;
        mainPanel.add(emailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        passwordField.setText("123456");
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(100, 30));

        registerButton = new JButton("Register here");
        registerButton.setBackground(Color.LIGHT_GRAY);
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(120, 30));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }
}
