package org.smart_job.view.jobs;

import lombok.Getter;
import org.smart_job.entity.JobStatus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

@Getter
public class JobTrackerContentPanel extends JPanel {

    // Search and Filter components
    private JTextField searchField;
    private JComboBox<String> statusFilterComboBox;
    private JButton clearSearchButton;
    private JButton filterButton;

    // Form components for CRUD operations
    private JTextField companyField;
    private JTextField titleField;
    private JTextField urlField;
    private JTextArea descriptionArea;
    private JComboBox<String> statusComboBox;
    private JTextArea notesArea;

    // Buttons
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;

    // Table
    private JTable jobTable;
    private DefaultTableModel tableModel;

    // Panels for easy access
    private JPanel formPanel;
    private JPanel buttonPanel;

    public JobTrackerContentPanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        initializeComponents();
        initUI();
    }

    private void initializeComponents() {
        // Search and filter components
        searchField = new JTextField(20);
        searchField.setToolTipText("Search by company or job title");
        searchField.setPreferredSize(new Dimension(200, 25));
        searchField.setMinimumSize(new Dimension(150, 25));

        statusFilterComboBox = new JComboBox<>(new String[]{
                "All Status", 
                JobStatus.WISH_LIST.getDisplayName(), 
                JobStatus.APPLIED.getDisplayName(), 
                JobStatus.INTERVIEW.getDisplayName(), 
                JobStatus.OFFER.getDisplayName(), 
                JobStatus.REJECTED.getDisplayName(), 
                JobStatus.ACCEPTED.getDisplayName()
        });

        // Search and Filter buttons
        clearSearchButton = createSmallButton("Clear", new Color(149, 165, 166));
        filterButton = createSmallButton("Filter", new Color(52, 152, 219));

        // Form components
        companyField = new JTextField(20);
        companyField.setPreferredSize(new Dimension(200, 25));
        companyField.setMinimumSize(new Dimension(150, 25));
        
        titleField = new JTextField(20);
        titleField.setPreferredSize(new Dimension(200, 25));
        titleField.setMinimumSize(new Dimension(150, 25));
        
        urlField = new JTextField(20);
        urlField.setPreferredSize(new Dimension(200, 25));
        urlField.setMinimumSize(new Dimension(150, 25));

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createLoweredBevelBorder());

        statusComboBox = new JComboBox<>(new String[]{
                JobStatus.WISH_LIST.getDisplayName(), 
                JobStatus.APPLIED.getDisplayName(), 
                JobStatus.INTERVIEW.getDisplayName(), 
                JobStatus.OFFER.getDisplayName(), 
                JobStatus.REJECTED.getDisplayName(), 
                JobStatus.ACCEPTED.getDisplayName()
        });

        notesArea = new JTextArea(3, 20);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setBorder(BorderFactory.createLoweredBevelBorder());

        // Buttons
        addButton = createStyledButton("Add Job", new Color(46, 204, 113));
        updateButton = createStyledButton("Update Job", new Color(52, 152, 219));
        deleteButton = createStyledButton("Delete Job", new Color(231, 76, 60));
        clearButton = createStyledButton("Clear Form", new Color(149, 165, 166));

        // Initialize table
        String[] columnNames = {"ID", "Company", "Job Title", "Date Applied", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        jobTable = new JTable(tableModel);
        jobTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jobTable.setRowHeight(25);
        jobTable.getTableHeader().setReorderingAllowed(false);

        // Set column widths
        if (jobTable.getColumnModel().getColumnCount() >= 5) {
            jobTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
            jobTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Company
            jobTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Job Title
            jobTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Date Applied
            jobTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Status
        }
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(100, 35));
        return button;
    }

    private JButton createSmallButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setPreferredSize(new Dimension(60, 25));
        return button;
    }

    private void initUI() {
        JPanel titlePanel = createTitlePanel();
        JPanel searchFilterPanel = createSearchFilterPanel();
        JPanel mainContentPanel = createMainContentPanel();

        // Create a top panel to hold both title and search/filter
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(searchFilterPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Job Tracker");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(52, 73, 94));

        JLabel subtitle = new JLabel("Track and manage the jobs you have applied");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(new Color(127, 140, 141));

        titlePanel.add(title, BorderLayout.NORTH);
        titlePanel.add(subtitle, BorderLayout.SOUTH);

        return titlePanel;
    }

    private JPanel createSearchFilterPanel() {
        JPanel searchFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchFilterPanel.setBorder(BorderFactory.createTitledBorder("Search & Filter"));
        searchFilterPanel.setBackground(Color.WHITE);
        ((TitledBorder) searchFilterPanel.getBorder()).setTitleColor(Color.BLACK);
        searchFilterPanel.setPreferredSize(new Dimension(0, 80));

        searchFilterPanel.add(new JLabel("Search:"));
        searchFilterPanel.add(searchField);
        searchFilterPanel.add(new JLabel("Filter by Status:"));
        searchFilterPanel.add(statusFilterComboBox);
        searchFilterPanel.add(filterButton);
        searchFilterPanel.add(clearSearchButton);

        return searchFilterPanel;
    }

    private JPanel createMainContentPanel() {
        JPanel mainContentPanel = new JPanel(new BorderLayout(15, 15));
        mainContentPanel.setBackground(Color.WHITE);

        formPanel = createFormPanel();
        JPanel tablePanel = createTablePanel();

        mainContentPanel.add(formPanel, BorderLayout.CENTER);
        mainContentPanel.add(tablePanel, BorderLayout.SOUTH);

        return mainContentPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Job Information"));
        formPanel.setBackground(Color.WHITE);
        ((TitledBorder) formPanel.getBorder()).setTitleColor(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Company and Title
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Company:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(companyField, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Job Title:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        formPanel.add(titleField, gbc);

        // Row 1: URL
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("URL:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(urlField, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        formPanel.add(statusComboBox, gbc);

        // Row 2: Description
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 0.3;
        formPanel.add(new JScrollPane(descriptionArea), gbc);

        // Row 3: Notes
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0; gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Notes:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 0.3;
        formPanel.add(new JScrollPane(notesArea), gbc);

        // Row 5: Buttons
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0; gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel = createButtonPanel();
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        return buttonPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Applied Jobs"));
        tablePanel.setBackground(Color.WHITE);
        ((TitledBorder) tablePanel.getBorder()).setTitleColor(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(jobTable);
        scrollPane.setPreferredSize(new Dimension(0, 250));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    // Public methods for Controller to interact with View

    public void clearForm() {
        companyField.setText("");
        titleField.setText("");
        urlField.setText("");
        descriptionArea.setText("");
        statusComboBox.setSelectedIndex(0);
        notesArea.setText("");
        jobTable.clearSelection();
    }

    public void populateForm(String company, String title,
                             String url, String description, String status, String notes) {
        companyField.setText(company);
        titleField.setText(title);
        urlField.setText(url);
        descriptionArea.setText(description);
        statusComboBox.setSelectedItem(status);
        notesArea.setText(notes);
    }

    // @Getter annotation provides access to all fields directly
    // Controller can access: view.getCompanyField().getText().trim()
    // Controller can access: view.getStatusComboBox().getSelectedItem().toString()
    // Controller can access: view.getJobTable().getSelectedRow()

    public void addTableRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void updateTableRow(int row, Object[] rowData) {
        for (int i = 0; i < rowData.length && i < tableModel.getColumnCount(); i++) {
            tableModel.setValueAt(rowData[i], row, i);
        }
    }

    public void removeTableRow(int row) {
        if (row >= 0 && row < tableModel.getRowCount()) {
            tableModel.removeRow(row);
        }
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public Object getTableValueAt(int row, int column) {
        return tableModel.getValueAt(row, column);
    }

    public int getTableRowCount() {
        return tableModel.getRowCount();
    }

    public void selectTableRow(int row) {
        if (row >= 0 && row < tableModel.getRowCount()) {
            jobTable.setRowSelectionInterval(row, row);
        }
    }

    // Methods to set event listeners (Controller will call these)

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addUpdateButtonListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addClearButtonListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }

    public void addSearchFieldListener(ActionListener listener) {
        searchField.addActionListener(listener);
    }

    public void addStatusFilterListener(ActionListener listener) {
        statusFilterComboBox.addActionListener(listener);
    }

    public void addTableMouseListener(MouseListener listener) {
        jobTable.addMouseListener(listener);
    }

    public void addClearSearchButtonListener(ActionListener listener) {
        clearSearchButton.addActionListener(listener);
    }

    public void addFilterButtonListener(ActionListener listener) {
        filterButton.addActionListener(listener);
    }

    // Clear search and filter
    public void clearSearchAndFilter() {
        searchField.setText("");
        statusFilterComboBox.setSelectedIndex(0); // "All Status"
    }

    // Validation support - Controller can access fields directly via @Getter
    public boolean isFormValid() {
        return !companyField.getText().trim().isEmpty() &&
                !titleField.getText().trim().isEmpty() &&
                !urlField.getText().trim().isEmpty();
    }

    public void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean showConfirmDialog(String message) {
        int result = JOptionPane.showConfirmDialog(
                this,
                message,
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );
        return result == JOptionPane.YES_OPTION;
    }

    public void focusCompanyField() {
        companyField.requestFocus();
    }

    public void focusTitleField() {
        titleField.requestFocus();
    }

    public void focusLocationField() {
        urlField.requestFocus();
    }
}
