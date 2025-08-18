package org.smart_job.view.dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardContentPanel extends JPanel{
    public DashboardContentPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(20, 10, 20, 10));
        setBackground(Color.WHITE);

        add(createHeader());
        add(createProfileOverview());
        add(createStatsPanel());
    }

    private JPanel createHeader() {
        JLabel title = new JLabel("Welcome to Smart_Job");
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel subtitle = new JLabel("This is overview for Smart_Job");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(Color.WHITE);
        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitle);
        return panel;
    }

    private JPanel createProfileOverview() {
        JPanel panel = new JPanel(new GridLayout(2, 3));
        panel.setBorder(BorderFactory.createTitledBorder("Profile Overview"));
        panel.setBackground(Color.WHITE);

        panel.add(labelBlock("Vị trí mong muốn", "Frontend Developer"));
        panel.add(labelBlock("CV đã upload", "duong_frontend_developer.pdf"));
        panel.add(labelBlock("Kĩ năng chính", "Java, Spring Boot, Gcloud..."));

        return panel;
    }

    private JPanel labelBlock(String title, String content) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);

        JLabel labelTitle = new JLabel(title);
        JLabel labelContent = new JLabel("<html><b>" + content + "</b></html>");

        p.add(labelTitle, BorderLayout.NORTH);
        p.add(labelContent, BorderLayout.CENTER);
        return p;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        panel.add(statBox("Tổng số job đã apply", "10"));
        panel.add(statBox("Tỷ lệ phản hồi", "90%"));
        panel.add(statBox("Đang chờ phản hồi", "4"));
        panel.add(statBox("Lời mời phỏng vấn", "1"));

        return panel;
    }

    private JPanel statBox(String title, String value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        p.setBackground(Color.WHITE);

        JLabel labelTitle = new JLabel(title);
        labelTitle.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel labelValue = new JLabel(value);
        labelValue.setFont(new Font("Arial", Font.BOLD, 22));
        labelValue.setHorizontalAlignment(SwingConstants.CENTER);

        p.add(labelTitle, BorderLayout.NORTH);
        p.add(labelValue, BorderLayout.CENTER);
        return p;
    }
}
