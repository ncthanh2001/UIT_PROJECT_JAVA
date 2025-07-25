package org.smart_job.view;

import org.smart_job.MainApp;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JPanel {
    private MainApp mainApp;
    private JPanel contentPanel;
    private JButton dashboardButton;
    private JButton jobTrackerButton;
    private JButton cvAnalysisButton;
    private JButton cvAnalysisButton1;
    private JButton cvAnalysisButton2;
    private JButton cvAnalysisButton3;
    private JButton cvAnalysisButton4;
    private JButton cvAnalysisButton5;
    private JButton cvAnalysisButton6;
    private JButton cvAnalysisButton7;

    public HomeFrame(MainApp mainApp) {
        this.mainApp = mainApp;
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Tạo sidebar và content panel
        JPanel sidebar = createSidebar();
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        showContentPanel(createDashboardPanel());
        highlightButton(dashboardButton);

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.LIGHT_GRAY);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Navigation buttons
        dashboardButton = createNavButton("Dashboard", "/images/nav_dashboard.png");
        jobTrackerButton = createNavButton("Job Tracker", "/images/nav_job_tracker.png");
        cvAnalysisButton = createNavButton("CV Analysis", "/images/nav_scan.png");
        cvAnalysisButton1 = createNavButton("CV Analysis", "/images/nav_scan.png");
        cvAnalysisButton2 = createNavButton("CV Analysis", "/images/nav_scan.png");
        cvAnalysisButton3 = createNavButton("CV Analysis", "/images/nav_scan.png");
        cvAnalysisButton4 = createNavButton("CV Analysis", "/images/nav_scan.png");
        cvAnalysisButton5 = createNavButton("CV Analysis", "/images/nav_scan.png");
        cvAnalysisButton6 = createNavButton("CV Analysis", "/images/nav_scan.png");
        cvAnalysisButton7 = createNavButton("CV Analysis", "/images/nav_scan.png");

        dashboardButton.addActionListener(e -> {
            showContentPanel(createDashboardPanel());
            highlightButton(dashboardButton);
        });
        jobTrackerButton.addActionListener(e -> {
            showContentPanel(createJobTrackerPanel());
            highlightButton(jobTrackerButton);
        });
        cvAnalysisButton.addActionListener(e -> {
            showContentPanel(createCVAnalysisPanel());
            highlightButton(cvAnalysisButton);
        });

        // User info panel
        JPanel userPanel = new JPanel();
        userPanel.setBackground(Color.LIGHT_GRAY);
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        userPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // User icon
        JLabel userIcon = new JLabel();
        java.net.URL iconUrl = getClass().getResource("/images/nav_user.png");
        if (iconUrl != null) {
            ImageIcon icon = new ImageIcon(iconUrl);
            Image scaledIcon = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            userIcon.setIcon(new ImageIcon(scaledIcon));
        }
        userPanel.add(userIcon);

        // User name and ID
        JPanel userInfo = new JPanel();
        userInfo.setBackground(Color.LIGHT_GRAY);
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));
        JLabel userName = new JLabel("User name here");
        userName.setFont(new Font("Arial", Font.BOLD, 16));
        userName.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel userId = new JLabel("ID: 12345678910");
        userId.setFont(new Font("Arial", Font.PLAIN, 12));
        userId.setAlignmentX(Component.LEFT_ALIGNMENT);
        userInfo.add(userName);
        userInfo.add(Box.createVerticalStrut(5));
        userInfo.add(userId);
        userPanel.add(userInfo);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutButton.addActionListener(e -> mainApp.showPanel(new LoginFrame(mainApp)));

        // Add components to sidebar
        sidebar.add(userPanel);

        sidebar.add(dashboardButton);
        sidebar.add(Box.createVerticalStrut(15));

        sidebar.add(jobTrackerButton);
        sidebar.add(Box.createVerticalStrut(15));

        sidebar.add(cvAnalysisButton);
        sidebar.add(Box.createVerticalGlue()); // Push logoutButton to the end of sidebar

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(logoutButton);
        sidebar.add(Box.createVerticalStrut(10));


        return sidebar;
    }

    private JButton createNavButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setActionCommand(text);

        java.net.URL iconUrl = getClass().getResource(iconPath);
        if (iconUrl != null) {
            ImageIcon icon = new ImageIcon(iconUrl);
            Image scaledIcon = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledIcon));
        }
        return button;
    }

    private void highlightButton(JButton selectedButton) {
        dashboardButton.setFont(new Font("Arial", Font.PLAIN, 14));
        dashboardButton.setBackground(Color.LIGHT_GRAY);
        jobTrackerButton.setFont(new Font("Arial", Font.PLAIN, 14));
        jobTrackerButton.setBackground(Color.LIGHT_GRAY);
        cvAnalysisButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cvAnalysisButton.setBackground(Color.LIGHT_GRAY);

        selectedButton.setFont(new Font("Arial", Font.BOLD, 14));
        selectedButton.setBackground(new Color(180, 180, 180));
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel("Welcome to Dashboard", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createJobTrackerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel("Job Tracker", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCVAnalysisPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel("CV Analysis", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private void showContentPanel(JPanel newPanel) {
        contentPanel.removeAll();
        contentPanel.add(newPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}