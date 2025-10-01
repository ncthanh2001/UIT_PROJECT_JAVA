package org.smart_job.view.dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class DashboardContentPanel extends JPanel{
    // Define color constants for better UI
    private static final Color PRIMARY_COLOR = new Color(59, 130, 246);  // Blue
    private static final Color SECONDARY_COLOR = new Color(249, 250, 251);  // Light gray
    private static final Color ACCENT_COLOR = new Color(16, 185, 129);  // Green
    private static final Color WARNING_COLOR = new Color(245, 158, 11);  // Yellow
    private static final Color DANGER_COLOR = new Color(239, 68, 68);  // Red
    private static final Color TEXT_PRIMARY = new Color(31, 41, 55);  // Dark gray
    private static final Color TEXT_SECONDARY = new Color(107, 114, 128);  // Medium gray
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(229, 231, 235);  // Light border

    // Components that need to be updated dynamically
    private JLabel titleLabel;
    private JLabel desiredPositionLabel;
    private JLabel cvFileLabel;
    private JLabel skillsLabel;
    private JLabel totalApplicationsValue;
    private JLabel responseRateValue;
    private JLabel pendingResponsesValue;
    private JLabel interviewInvitesValue;

    // Job suggestions components
    private JPanel jobSuggestionsPanel;
    private JPanel jobListPanel;
    private JButton addJobButton;
    private JButton analyzeButton;
    private JButton viewAllButton;

    public DashboardContentPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setBackground(SECONDARY_COLOR);

        // Create main content panel with better spacing
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(SECONDARY_COLOR);

        mainContent.add(createHeader());
        mainContent.add(Box.createVerticalStrut(30));
        mainContent.add(createProfileOverview());
        mainContent.add(Box.createVerticalStrut(30));
        mainContent.add(createStatsPanel());
        mainContent.add(Box.createVerticalStrut(30));
        mainContent.add(createJobSuggestionsSection());

        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Welcome message
        titleLabel = new JLabel("Chào mừng trở lại, User!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);

        JLabel subtitle = new JLabel("Đây là tổng quan về hoạt động tìm việc của bạn");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_SECONDARY);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createProfileOverview() {
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(SECONDARY_COLOR);

        // Create title for the section
        JLabel sectionTitle = new JLabel("Tổng quan hồ sơ");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        sectionTitle.setForeground(TEXT_PRIMARY);
        sectionTitle.setBorder(new EmptyBorder(0, 0, 15, 0));

        // Create the profile card
        JPanel profileCard = new JPanel();
        profileCard.setLayout(new BoxLayout(profileCard, BoxLayout.Y_AXIS));
        profileCard.setBackground(CARD_BACKGROUND);
        profileCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(25, 25, 25, 25)
        ));

        // Create profile info grid
        JPanel profileGrid = new JPanel(new GridLayout(1, 3, 20, 0));
        profileGrid.setBackground(CARD_BACKGROUND);

        profileGrid.add(createProfileInfoBlock("Vị trí mong muốn", "Frontend Developer", PRIMARY_COLOR, true));
        profileGrid.add(createProfileInfoBlock("CV đã upload", "duong_frontend_developer.pdf", ACCENT_COLOR, false));
        profileGrid.add(createProfileInfoBlock("Kĩ năng chính", "Java, Spring Boot, GCloud, Microservice, ...", WARNING_COLOR, false));

        profileCard.add(profileGrid);

        containerPanel.add(sectionTitle, BorderLayout.NORTH);
        containerPanel.add(profileCard, BorderLayout.CENTER);

        return containerPanel;
    }

    private JPanel createProfileInfoBlock(String title, String content, Color accentColor, boolean isDesiredPosition) {
        JPanel block = new JPanel();
        block.setLayout(new BoxLayout(block, BoxLayout.Y_AXIS));
        block.setBackground(CARD_BACKGROUND);

        // Title with accent color
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_SECONDARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Content with bold font
        JLabel contentLabel = new JLabel("<html><div style='width: 200px;'><b>" + content + "</b></div></html>");
        contentLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        contentLabel.setForeground(TEXT_PRIMARY);
        contentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Store references to labels that need to be updated
        if (isDesiredPosition) {
            desiredPositionLabel = contentLabel;
        } else if (title.equals("CV đã upload")) {
            cvFileLabel = contentLabel;
        } else if (title.equals("Kĩ năng chính")) {
            skillsLabel = contentLabel;
        }

        // Add colored accent line
        JPanel accentLine = new JPanel();
        accentLine.setBackground(accentColor);
        accentLine.setMaximumSize(new Dimension(50, 3));
        accentLine.setAlignmentX(Component.LEFT_ALIGNMENT);

        block.add(titleLabel);
        block.add(Box.createVerticalStrut(8));
        block.add(contentLabel);
        block.add(Box.createVerticalStrut(10));
        block.add(accentLine);

        return block;
    }

    private JPanel createStatsPanel() {
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(SECONDARY_COLOR);

        // Stats grid with proper spacing
        JPanel statsGrid = new JPanel(new GridLayout(1, 4, 20, 0));
        statsGrid.setBackground(SECONDARY_COLOR);

        statsGrid.add(createStatCard("Tổng số job đã apply", "10", PRIMARY_COLOR, "totalApplications"));
        statsGrid.add(createStatCard("Tỷ lệ phản hồi", "90%", ACCENT_COLOR, "responseRate"));
        statsGrid.add(createStatCard("Đang chờ phản hồi", "4", WARNING_COLOR, "pendingResponses"));
        statsGrid.add(createStatCard("Lời mời phỏng vấn", "1", DANGER_COLOR, "interviewInvites"));

        containerPanel.add(statsGrid, BorderLayout.CENTER);
        return containerPanel;
    }

    private JPanel createStatCard(String title, String value, Color accentColor, String statType) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_SECONDARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Value with accent color
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        valueLabel.setForeground(accentColor);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Store references to value labels for updates
        switch (statType) {
            case "totalApplications":
                totalApplicationsValue = valueLabel;
                break;
            case "responseRate":
                responseRateValue = valueLabel;
                break;
            case "pendingResponses":
                pendingResponsesValue = valueLabel;
                break;
            case "interviewInvites":
                interviewInvitesValue = valueLabel;
                break;
        }

        // Add top colored border
        JPanel topBorder = new JPanel();
        topBorder.setBackground(accentColor);
        topBorder.setPreferredSize(new Dimension(0, 4));

        card.add(topBorder, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createJobSuggestionsSection() {
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(SECONDARY_COLOR);

        // Section header with action buttons
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(SECONDARY_COLOR);

        JLabel sectionTitle = new JLabel("Gợi ý việc làm mới");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        sectionTitle.setForeground(TEXT_PRIMARY);

        // Action buttons panel
        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionButtonsPanel.setBackground(SECONDARY_COLOR);

        addJobButton = createActionButton("+ Thêm Job", PRIMARY_COLOR);
        analyzeButton = createActionButton("🔍 Phân tích AI", ACCENT_COLOR);
        viewAllButton = createActionButton("📋 Xem tất cả", WARNING_COLOR);

        actionButtonsPanel.add(addJobButton);
        actionButtonsPanel.add(analyzeButton);
        actionButtonsPanel.add(viewAllButton);

        headerPanel.add(sectionTitle, BorderLayout.WEST);
        headerPanel.add(actionButtonsPanel, BorderLayout.EAST);

        // Job suggestions content with scrollable area
        jobSuggestionsPanel = new JPanel();
        jobSuggestionsPanel.setLayout(new BoxLayout(jobSuggestionsPanel, BoxLayout.Y_AXIS));
        jobSuggestionsPanel.setBackground(CARD_BACKGROUND);
        jobSuggestionsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        // Job list container
        jobListPanel = new JPanel();
        jobListPanel.setLayout(new BoxLayout(jobListPanel, BoxLayout.Y_AXIS));
        jobListPanel.setBackground(CARD_BACKGROUND);

        // Add sample jobs initially
        addSampleJobs();

        jobSuggestionsPanel.add(jobListPanel);

        // Create scrollable wrapper for job suggestions
        JScrollPane scrollPane = new JScrollPane(jobSuggestionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(0, 400)); // Set fixed height for scrollable area
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling

        containerPanel.add(headerPanel, BorderLayout.NORTH);
        containerPanel.add(Box.createVerticalStrut(15), BorderLayout.CENTER);
        containerPanel.add(scrollPane, BorderLayout.SOUTH);

        return containerPanel;
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void addSampleJobs() {
        // Sample job data - will be replaced with real data later
        addJobCard("Senior Java Developer", "TechCorp Vietnam", "Ho Chi Minh City", "30-50M VND", "Java, Spring Boot, Microservices");
        addJobCard("Frontend Developer", "Digital Solutions", "Ha Noi", "25-35M VND", "React, JavaScript, TypeScript");
        addJobCard("Full Stack Developer", "Innovation Lab", "Da Nang", "28-40M VND", "Java, React, PostgreSQL");
        addJobCard("DevOps Engineer", "Cloud Systems", "Ho Chi Minh City", "35-55M VND", "AWS, Docker, Kubernetes");
    }

    private void addJobCard(String title, String company, String location, String salary, String skills) {
        JPanel jobCard = new JPanel(new BorderLayout());
        jobCard.setBackground(SECONDARY_COLOR);
        jobCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(219, 234, 254), 2),
            new EmptyBorder(15, 15, 15, 15)
        ));
        jobCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Left side - Job info
        JPanel jobInfoPanel = new JPanel();
        jobInfoPanel.setLayout(new BoxLayout(jobInfoPanel, BoxLayout.Y_AXIS));
        jobInfoPanel.setBackground(SECONDARY_COLOR);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_PRIMARY);

        JLabel companyLabel = new JLabel(company + " • " + location);
        companyLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        companyLabel.setForeground(TEXT_SECONDARY);

        JLabel salaryLabel = new JLabel("💰 " + salary);
        salaryLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        salaryLabel.setForeground(ACCENT_COLOR);

        JLabel skillsLabel = new JLabel("🔧 " + skills);
        skillsLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        skillsLabel.setForeground(TEXT_SECONDARY);

        jobInfoPanel.add(titleLabel);
        jobInfoPanel.add(Box.createVerticalStrut(5));
        jobInfoPanel.add(companyLabel);
        jobInfoPanel.add(Box.createVerticalStrut(5));
        jobInfoPanel.add(salaryLabel);
        jobInfoPanel.add(Box.createVerticalStrut(5));
        jobInfoPanel.add(skillsLabel);

        // Right side - Action button
        JButton applyButton = new JButton("Apply Now");
        applyButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        applyButton.setForeground(Color.WHITE);
        applyButton.setBackground(PRIMARY_COLOR);
        applyButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        applyButton.setFocusPainted(false);
        applyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jobCard.add(jobInfoPanel, BorderLayout.CENTER);
        jobCard.add(applyButton, BorderLayout.EAST);

        jobListPanel.add(jobCard);
        jobListPanel.add(Box.createVerticalStrut(10));
    }

    /**
     * Update dashboard with real data from the controller
     */
    public void updateDashboardData(String userName, String desiredPosition, String cvFileName,
                                  String skills, int totalApplications, double responseRate,
                                  int pendingResponses, int interviewInvites) {
        // Update header
        if (titleLabel != null) {
            titleLabel.setText("Chào mừng chờ lại, " + userName + "!");
        }

        // Update profile info
        if (desiredPositionLabel != null) {
            desiredPositionLabel.setText("<html><div style='width: 200px;'><b>" + desiredPosition + "</b></div></html>");
        }
        if (cvFileLabel != null) {
            cvFileLabel.setText("<html><div style='width: 200px;'><b>" + cvFileName + "</b></div></html>");
        }
        if (skillsLabel != null) {
            skillsLabel.setText("<html><div style='width: 200px;'><b>" + skills + "</b></div></html>");
        }

        // Update statistics
        if (totalApplicationsValue != null) {
            totalApplicationsValue.setText(String.valueOf(totalApplications));
        }
        if (responseRateValue != null) {
            responseRateValue.setText(String.format("%.0f%%", responseRate));
        }
        if (pendingResponsesValue != null) {
            pendingResponsesValue.setText(String.valueOf(pendingResponses));
        }
        if (interviewInvitesValue != null) {
            interviewInvitesValue.setText(String.valueOf(interviewInvites));
        }

        // Refresh the display
        revalidate();
        repaint();
    }

    /**
     * Set action listeners for navigation buttons
     */
    public void setNavigationListeners(ActionListener addJobListener,
                                     ActionListener analyzeListener,
                                     ActionListener viewAllListener) {
        if (addJobButton != null) {
            addJobButton.addActionListener(addJobListener);
        }
        if (analyzeButton != null) {
            analyzeButton.addActionListener(analyzeListener);
        }
        if (viewAllButton != null) {
            viewAllButton.addActionListener(viewAllListener);
        }
    }

    /**
     * Update job suggestions with real data
     */
    public void updateJobSuggestions(java.util.List<org.smart_job.entity.Job> jobs) {
        jobListPanel.removeAll();

        if (jobs == null || jobs.isEmpty()) {
            JLabel noJobsLabel = new JLabel("Không có gợi ý việc làm nào");
            noJobsLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
            noJobsLabel.setForeground(TEXT_SECONDARY);
            noJobsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jobListPanel.add(noJobsLabel);
        } else {
            // Show maximum 4 jobs
            int maxJobs = Math.min(4, jobs.size());
            for (int i = 0; i < maxJobs; i++) {
                org.smart_job.entity.Job job = jobs.get(i);
                addJobCard(
                    job.getTitle() != null ? job.getTitle() : "Chưa có tiêu đề",
                    job.getCompanyName() != null ? job.getCompanyName() : "Chưa có công ty",
                    job.getLocation() != null ? job.getLocation() : "Chưa có địa điểm",
                    "Thỏa thuận", // No salary field in Job entity
                    job.getDescription() != null ?
                        (job.getDescription().length() > 50 ?
                            job.getDescription().substring(0, 50) + "..." :
                            job.getDescription()) : "Chưa có mô tả"
                );
            }
        }

        revalidate();
        repaint();
    }
}
