package org.smart_job.view.jobs;

import lombok.Getter;
import org.smart_job.entity.Job;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URI;
import java.util.List;

@Getter
public class ListJobsContentPannel extends JPanel {
    private final JTextField searchField;
    private final JComboBox<String> countryComboBox;
    private final JComboBox<String> cityComboBox;
    private final JButton searchButton;
    private JButton detailBtn;
    private JButton applyBtn;

    // Pagination control
    private final JButton firstPageButton;
    private final JButton prevPageButton;
    private final JButton nextPageButton;
    private final JButton lastPageButton;
    private final JLabel pageLabel;
    public int currentPage = 1; // Mặc định chọn page 1
    public int totalPages = 1; // Khởi tạo tổng page
    public final int pageSize = 10; // Số lượng jobs trong 1 page

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
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
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
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter & Search"));
        filterPanel.setBackground(Color.WHITE);
        ((TitledBorder) filterPanel.getBorder()).setTitleColor(Color.BLACK);

        // Hàng 1: search field
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row1.setBackground(Color.WHITE);
        searchField = new JTextField(25);
        searchField.setToolTipText("Search job title");
        row1.add(searchField);

        // Hàng 2: country + city
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row2.setBackground(Color.WHITE);
        countryComboBox = new JComboBox<>();
        countryComboBox.addItem("All Countries");
        cityComboBox = new JComboBox<>();
        cityComboBox.addItem("All Cities");
        row2.add(new JLabel("Country:"));
        row2.add(countryComboBox);
        row2.add(new JLabel("City:"));
        row2.add(cityComboBox);

        // Hàng 3: search button
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        row3.setBackground(Color.WHITE);
        searchButton = new JButton("Search");
        row3.add(searchButton);

        filterPanel.add(row1);
        filterPanel.add(row2);
        filterPanel.add(row3);

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

        // Pagination panel
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        paginationPanel.setBackground(Color.WHITE);
        firstPageButton = new JButton("<< First");
        prevPageButton = new JButton("< Prev");
        pageLabel = new JLabel("Page 1 / 1");
        nextPageButton = new JButton("Next >");
        lastPageButton = new JButton("Last >>");
        paginationPanel.add(firstPageButton);
        paginationPanel.add(prevPageButton);
        paginationPanel.add(pageLabel);
        paginationPanel.add(nextPageButton);
        paginationPanel.add(lastPageButton);

        listPanel.add(paginationPanel, BorderLayout.SOUTH);

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
        detailBtn = new JButton("View Details");
        applyBtn = new JButton("Apply Now");
        applyBtn.setBackground(new Color(0, 128, 0));
        applyBtn.setForeground(Color.WHITE);

        detailBtn.addActionListener(e -> showJobDetails(job, skills));

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

    private void showJobDetails(Job job, List<String> skills) {
        // Find the parent window dynamically
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        // Create custom dialog
        JDialog dialog = new JDialog((Frame) parentWindow, "Job Details - " + job.getTitle(), true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setBackground(new Color(245, 245, 245));
        dialog.setLayout(new BorderLayout());
        dialog.setSize(700, 650); // Increased size for more breathing room
        dialog.setLocationRelativeTo(parentWindow);

        // Create main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 245, 245));

        // Job title
        JLabel titleLabel = new JLabel(job.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(33, 37, 41));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15)); // Increased vertical spacing

        // Company
        JLabel companyLabel = new JLabel("🏢 " + job.getCompanyName());
        companyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        companyLabel.setForeground(new Color(80, 80, 80));
        companyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(companyLabel);
        panel.add(Box.createVerticalStrut(12)); // Increased vertical spacing

        // Location
        JLabel locationLabel = new JLabel(job.getCity() + ", " + job.getCountry());
        locationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        locationLabel.setForeground(new Color(80, 80, 80));
        locationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(locationLabel);
        panel.add(Box.createVerticalStrut(12)); // Increased vertical spacing

        // URL
        JLabel urlLabel = new JLabel("<html><a href='" + job.getUrl() + "'>" + job.getUrl() + "</a></html>");
        urlLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        urlLabel.setForeground(new Color(0, 102, 204));
        urlLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        urlLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    Desktop.getDesktop().browse(new URI(job.getUrl()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        urlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(urlLabel);
        panel.add(Box.createVerticalStrut(12)); // vertical spacing

        // Skills
        JLabel skillsLabel = new JLabel("Skills: " + String.join(", ", skills));
        skillsLabel.setFont(new Font("Segue UI", Font.ITALIC, 14));
        skillsLabel.setForeground(new Color(50, 50, 50));
        skillsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(skillsLabel);
        panel.add(Box.createVerticalStrut(20)); // Increased vertical spacing

        // Description in JTextArea with modern styling and more padding
        JTextArea descArea = new JTextArea(job.getDescription());
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setFont(new Font("Segue UI", Font.PLAIN, 13));
        descArea.setBackground(new Color(255, 255, 255));
        descArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15))); // padding
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setPreferredSize(new Dimension(500, 220));
        descScroll.setBorder(null);
        descScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(descScroll);

        // Add close button at the bottom with padding
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10)); // Added padding
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segue UI", Font.PLAIN, 14));
        closeButton.setBackground(new Color(0, 102, 204));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // button padding
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);

        // Add panels to dialog
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Show dialog
        dialog.setVisible(true);
    }
}
