package org.smart_job.view.jobs;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Getter
public class JobTrackerContentPanel extends JPanel {
    private final JTextField searchField;
    private final JComboBox<String> statusComboBox;
    private final JButton addJobButton;
    private final JTable jobTable;

    public JobTrackerContentPanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Job Tracker");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setOpaque(true);
        title.setBackground(Color.WHITE);

        JLabel subtitle = new JLabel("Track and manage the jobs you have applied");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setOpaque(true);
        subtitle.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(title, BorderLayout.NORTH);
        titlePanel.add(subtitle, BorderLayout.SOUTH);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter and Search"));
        filterPanel.setBackground(Color.WHITE);
        ((TitledBorder) filterPanel.getBorder()).setTitleColor(Color.BLACK);
        filterPanel.setPreferredSize(new Dimension(0, 80));

        searchField = new JTextField(20);
        searchField.setToolTipText("Search title");

        statusComboBox = new JComboBox<>(new String[]{"All status", "Pending", "Interview", "Rejected", "Offer"});
        addJobButton = new JButton("+ Add job");

        filterPanel.add(searchField);
        filterPanel.add(statusComboBox);
        filterPanel.add(addJobButton);

        // Table Panel
        JPanel listPanel = new JPanel(new BorderLayout(10, 10));
        listPanel.setBorder(BorderFactory.createTitledBorder("List job apply (1)"));
        listPanel.setBackground(Color.WHITE);
        ((TitledBorder) listPanel.getBorder()).setTitleColor(Color.BLACK);

        JLabel desc = new JLabel("Manage and track the status of jobs you have applied for");
        desc.setFont(new Font("Arial", Font.PLAIN, 12));
        listPanel.add(desc, BorderLayout.NORTH);

        String[] columnNames = {"Company", "Job Title", "Date Applied", "Status", "Actions"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4; // allow status and actions to edit
            }
        };
        jobTable = new JTable(model);
        jobTable.setRowHeight(30);

        // Add row manually
        model.addRow(new Object[]{
                "Google Company",
                "Java Developer",
                "2025-07-28",
                createStatusCombo(),
                createDetailButton()
        });

        JScrollPane scrollPane = new JScrollPane(jobTable);
        scrollPane.setPreferredSize(new Dimension(0, 300));
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Combine all
        add(titlePanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.CENTER);
        add(listPanel, BorderLayout.SOUTH);
    }

    private JComboBox<String> createStatusCombo() {
        return new JComboBox<>(new String[]{"Pending", "Interview", "Rejected", "Offer"});
    }

    private JButton createDetailButton() {
        return new JButton("Details");
    }
}
