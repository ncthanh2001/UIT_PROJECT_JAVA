package org.smart_job.view.jobs;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ListJobsContentPannel extends JPanel {
    private final JTextField searchField;
    private final JButton searchButton;
    private final JLabel jobCountLabel;
    private final JPanel jobsContainer;

    public ListJobsContentPannel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Title & Subtitle
        JLabel title = new JLabel("Jobs");
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel subtitle = new JLabel("Discover job opportunities that match your profile");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter & Search"));
        filterPanel.setBackground(Color.WHITE);
        ((TitledBorder) filterPanel.getBorder()).setTitleColor(Color.BLACK);

        searchField = new JTextField(25);
        searchField.setToolTipText("Search company name and jobs");
        searchButton = new JButton("Search");

        filterPanel.add(searchField);
        filterPanel.add(searchButton);

        // Header + Filter
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.SOUTH);

        // Job List Section
        JPanel listPanel = new JPanel(new BorderLayout(10, 10));
        listPanel.setBackground(Color.WHITE);

        jobCountLabel = new JLabel("Jobs found: 0");
        jobCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        listPanel.add(jobCountLabel, BorderLayout.NORTH);

        jobsContainer = new JPanel();
        jobsContainer.setLayout(new BoxLayout(jobsContainer, BoxLayout.Y_AXIS));
        jobsContainer.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(jobsContainer);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // scroll mượt hơn
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // TopPanel ở trên, listPanel chiếm phần còn lại
        add(topPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);

        // --- Demo data ---
        addJobCard("Java Developer", "Google", "Hanoi, Vietnam",
                "Chúng tôi đang tìm kiếm ứng viên tiềm năng cho công ty Google với kinh nghiệm Java, Spring Boot...",
                Arrays.asList("Java", "Spring Boot", "SQL", "API", "AWS"), "95%");

        addJobCard("Frontend Engineer", "Facebook", "HCMC, Vietnam",
                "Cơ hội làm việc với React, hệ thống scale lớn, tham gia phát triển tính năng realtime...",
                Arrays.asList("React", "JavaScript", "CSS", "REST API", "Git"), "89%");

        addJobCard("Backend Engineer", "Amazon", "Da Nang, Vietnam",
                "Xây dựng dịch vụ microservice hiệu năng cao, xử lý hàng triệu request mỗi ngày...",
                Arrays.asList("Java", "Spring Cloud", "Docker", "Kubernetes", "MySQL"), "76%");

        addJobCard("Mobile Developer (iOS)", "Apple", "Hanoi, Vietnam",
                "Phát triển ứng dụng iOS với Swift/SwiftUI, tham gia vào hệ sinh thái Apple...",
                Arrays.asList("Swift", "SwiftUI", "UIKit", "REST API", "Git"), "82%");

        addJobCard("DevOps Engineer", "Netflix", "Remote",
                "Thiết kế CI/CD pipeline, tối ưu hạ tầng cloud, monitoring hệ thống 24/7...",
                Arrays.asList("AWS", "Terraform", "Jenkins", "Prometheus", "Kubernetes"), "68%");

        addJobCard("AI Researcher", "OpenAI", "HCMC, Vietnam",
                "Nghiên cứu và phát triển mô hình AI thế hệ mới, xử lý ngôn ngữ tự nhiên...",
                Arrays.asList("Python", "PyTorch", "NLP", "Deep Learning", "ML Ops"), "92%");

        addJobCard("Data Scientist", "Microsoft", "Hanoi, Vietnam",
                "Phân tích dữ liệu lớn, xây dựng mô hình Machine Learning để dự đoán xu hướng...",
                Arrays.asList("Python", "Pandas", "Scikit-learn", "SQL", "Azure"), "73%");

        addJobCard("UI/UX Designer", "Figma", "Remote",
                "Thiết kế giao diện hiện đại, trải nghiệm người dùng mượt mà cho ứng dụng web...",
                Arrays.asList("Figma", "Adobe XD", "User Research", "Wireframe", "Prototyping"), "65%");

        addJobCard("Cybersecurity Engineer", "Cisco", "HCMC, Vietnam",
                "Triển khai giải pháp bảo mật mạng, kiểm thử xâm nhập, phát hiện lỗ hổng...",
                Arrays.asList("Network Security", "PenTest", "Linux", "SIEM", "Python"), "48%");

        addJobCard("QA Automation Engineer", "Tiki", "Hanoi, Vietnam",
                "Viết test automation, đảm bảo chất lượng sản phẩm trước khi release...",
                Arrays.asList("Selenium", "JUnit", "Cucumber", "Java", "CI/CD"), "55%");

    }

    public void addJobCard(String title, String company, String location, String desc, List<String> skills, String matchPercent) {
        JPanel jobCard = new JPanel();
        jobCard.setLayout(new BorderLayout(10, 10));
        jobCard.setBackground(Color.WHITE);
        jobCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        // Border + padding
        jobCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));

        // Header: Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Header: Match %
        JLabel matchLabel = new JLabel(matchPercent);
        matchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        // Parse số từ "95%"
        int percentValue = 0;
        try {
            percentValue = Integer.parseInt(matchPercent.replace("%", "").trim());
        } catch (NumberFormatException ex) {
            percentValue = 0; // fallback nếu lỗi parse
        }
        // Đổi màu theo % match
        if (percentValue >= 80) {
            matchLabel.setForeground(new Color(0, 128, 0)); // xanh lá
        } else if (percentValue >= 50) {
            matchLabel.setForeground(new Color(255, 140, 0)); // cam
        } else {
            matchLabel.setForeground(Color.RED); // đỏ
        }

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.add(titleLabel, BorderLayout.WEST);
        header.add(matchLabel, BorderLayout.EAST);

        // Company & location
        JLabel companyLabel = new JLabel(company);
        companyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel locationLabel = new JLabel(location);
        locationLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        // Description (ngắn gọn, auto wrap)
        JLabel descLabel = new JLabel("<html>" + desc + "</html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Skills
        String skillText = String.join(", ", skills.subList(0, Math.min(5, skills.size())));
        JLabel skillsLabel = new JLabel("Skills: " + skillText);
        skillsLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Actions
        JButton detailBtn = new JButton("View Details");
        JButton applyBtn = new JButton("Apply Now");
        applyBtn.setBackground(new Color(0, 128, 0));
        applyBtn.setForeground(Color.WHITE);
        applyBtn.setFocusPainted(false);

        // Event View Details
        detailBtn.addActionListener(e -> showJobDetailsDialog(title, company, location, desc, skills, matchPercent));

        // Event: Apply Now
        applyBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có đồng ý gửi CV của bạn cho nhà tuyển dụng " + company + " không?",
                    "Xác nhận ứng tuyển",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(
                        this,
                        "CV của bạn đã được gửi tới " + company + ". Chúc bạn may mắn!",
                        "Ứng tuyển thành công",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.add(detailBtn);
        actionPanel.add(applyBtn);

        // Body
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(Color.WHITE);
        body.add(companyLabel);
        body.add(locationLabel);
        body.add(descLabel);
        body.add(skillsLabel);

        jobCard.add(header, BorderLayout.NORTH);
        jobCard.add(body, BorderLayout.CENTER);
        jobCard.add(actionPanel, BorderLayout.SOUTH);

        jobsContainer.add(jobCard);
        jobsContainer.add(Box.createVerticalStrut(10));

        // Update count
        jobCountLabel.setText("Jobs found: " + jobsContainer.getComponentCount() / 2);
    }

    private void showJobDetailsDialog(String title, String company, String location, String desc, List<String> skills, String matchPercent) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Job Details", true);
        dialog.setSize(550, 450);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel(title + " (" + matchPercent + " match)");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 60, 150));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));

        // Company
        JLabel companyLabel = new JLabel("Company: " + company);
        companyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(companyLabel);

        // Location
        JLabel locationLabel = new JLabel("Location: " + location);
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(locationLabel);
        panel.add(Box.createVerticalStrut(10));

        // Description
        JLabel descTitle = new JLabel("Description:");
        descTitle.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(descTitle);

        JTextArea descArea = new JTextArea(desc);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setEditable(false);
        descArea.setFont(new Font("Arial", Font.PLAIN, 13));
        descArea.setBackground(new Color(248, 248, 248));
        descArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setPreferredSize(new Dimension(500, 120));
        descScroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.add(descScroll);
        panel.add(Box.createVerticalStrut(10));

        // Skills
        JLabel skillsLabel = new JLabel("Skills required: " + String.join(", ", skills));
        skillsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(skillsLabel);

        // Buttons
        JPanel bottomBtn = getJPanel(company, dialog);

        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(bottomBtn, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel getJPanel(String company, JDialog dialog) {
        JButton applyBtn = new JButton("Apply Now");
        JButton closeBtn = new JButton("Close");

        applyBtn.setBackground(new Color(0, 128, 0));
        applyBtn.setForeground(Color.WHITE);
        applyBtn.setFocusPainted(false);

        closeBtn.setBackground(new Color(200, 200, 200));
        closeBtn.setFocusPainted(false);

        applyBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    dialog,
                    "Bạn có đồng ý gửi CV của bạn cho nhà tuyển dụng " + company + " không?",
                    "Xác nhận ứng tuyển",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(
                        dialog,
                        "CV của bạn đã được gửi tới " + company + ". Chúc bạn may mắn!",
                        "Ứng tuyển thành công",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dialog.dispose();
            }
        });

        closeBtn.addActionListener(e -> dialog.dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(Color.WHITE);
        bottom.add(applyBtn);
        bottom.add(closeBtn);
        return bottom;
    }

}
