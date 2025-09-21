package org.smart_job.view.jobs;

import lombok.Getter;
import org.smart_job.entity.Job;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

@Getter
public class ListJobsContentPannel extends JPanel {
    private final JTextField searchField;
    private final JButton searchButton;
    private final JLabel jobCountLabel;
    private final JPanel jobsContainer;

    public String getSearchText() {
        return searchField.getText();
    }

    public void clearJobs() {
        jobsContainer.removeAll();
        jobCountLabel.setText("Jobs found: 0");
        revalidate();
        repaint();
    }

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
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
    }

    public void addJobCard(Job job, String matchPercent, List<String> skills) {
        JPanel jobCard = new JPanel(new BorderLayout(10, 10));
        jobCard.setBackground(Color.WHITE);
        jobCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        jobCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));

        // Header: Title + Match %
        JLabel titleLabel = new JLabel(job.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel matchLabel = new JLabel(matchPercent);
        matchLabel.setFont(new Font("Arial", Font.BOLD, 14));

        int percentValue;
        try {
            percentValue = Integer.parseInt(matchPercent.replace("%", "").trim());
        } catch (NumberFormatException ex) {
            percentValue = 0;
        }
        if (percentValue >= 80) {
            matchLabel.setForeground(new Color(0, 128, 0));
        } else if (percentValue >= 50) {
            matchLabel.setForeground(new Color(255, 140, 0));
        } else {
            matchLabel.setForeground(Color.RED);
        }

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.add(titleLabel, BorderLayout.WEST);
        header.add(matchLabel, BorderLayout.EAST);

        // Company & location
        JLabel companyLabel = new JLabel(job.getCompanyName());
        companyLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel locationLabel = new JLabel(job.getCity() + ", " + job.getCountry());
        locationLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        // Description
        JLabel descLabel = new JLabel("<html>" + job.getDescription() + "</html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Skills
        String skillText = skills.isEmpty() ? "No skills listed"
                : String.join(", ", skills.subList(0, Math.min(5, skills.size())));
        JLabel skillsLabel = new JLabel("Skills: " + skillText);
        skillsLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Actions
        JButton detailBtn = new JButton("View Details");
        JButton applyBtn = new JButton("Apply Now");
        applyBtn.setBackground(new Color(0, 128, 0));
        applyBtn.setForeground(Color.WHITE);

        detailBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Job: " + job.getTitle() + "\nCompany: " + job.getCompanyName() +
                        "\nLocation: " + job.getCity() + ", " + job.getCountry() +
                        "\nURL: " + job.getUrl() +
                        "\nSkills: " + String.join(", ", skills)
        ));

        applyBtn.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "CV của bạn đã được gửi tới " + job.getCompanyName() + ".",
                "Ứng tuyển thành công",
                JOptionPane.INFORMATION_MESSAGE
        ));

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

        jobCountLabel.setText("Jobs found: " + (jobsContainer.getComponentCount() / 2));
    }
}
