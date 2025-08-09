package org.smart_job.view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class BaseLayoutView extends JFrame {
    private final JPanel contentPanel;

    private final JButton dashboardButton;
    private final JButton jobsButton;
    private final JButton cvAnalysisButton;
    private final JButton profileButton;
    private final JButton loginButton;
    private final JButton jobTrackerButton;


    public BaseLayoutView() {
        setTitle("Smart Job Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(33, 37, 41));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        dashboardButton = createNavButton("Dashboard");
        jobTrackerButton = createNavButton("Job Tracker");
        jobsButton = createNavButton("Jobs");
        cvAnalysisButton = createNavButton("CV Analysis");
        profileButton = createNavButton("Profile");
        loginButton = createNavButton("Login");
        sidebar.add(dashboardButton);
        sidebar.add(jobTrackerButton);
        sidebar.add(jobsButton);
        sidebar.add(cvAnalysisButton);
        sidebar.add(profileButton);
        sidebar.add(loginButton);
        add(sidebar, BorderLayout.WEST);

        // Content panel placeholder
        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        return btn;
    }

    public void setContent(JPanel newViewPanel) {
        contentPanel.removeAll();
        contentPanel.add(newViewPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void setContent(JPanel newViewPanel, String title) {
        contentPanel.removeAll();
        contentPanel.add(newViewPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
