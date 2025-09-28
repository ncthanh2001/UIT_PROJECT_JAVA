package org.smart_job.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.entity.JobApplication;
import org.smart_job.entity.JobStatus;
import org.smart_job.entity.User;
import org.smart_job.service.JobApplicationService;
import org.smart_job.service.impl.JobApplicationServiceImpl;
import org.smart_job.session.UserSession;
import org.smart_job.util.DateUtils;
import org.smart_job.view.jobs.JobTrackerContentPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class JobsTrackerController {
    private static final Logger logger = LogManager.getLogger(JobsTrackerController.class);

    private final JobTrackerContentPanel view;
    private final JobApplicationService jobApplicationService;
    private final User currentUser;
    
    private List<JobApplication> jobApplications;
    private JobApplication selectedJobApplication;

    public JobsTrackerController(JobTrackerContentPanel view) {
        this.view = view;
        this.jobApplicationService = new JobApplicationServiceImpl();
        this.currentUser = UserSession.getInstance().getCurrentUser();
        
        initializeController();
    }

    private void initializeController() {
        if (currentUser == null) {
            view.showErrorMessage("User session not found. Please login again.");
            return;
        }
        
        setupEventListeners();
        loadJobApplications();
        view.focusCompanyField();
    }

    private void setupEventListeners() {
        // Button listeners
        view.addAddButtonListener(e -> handleAddJob());
        view.addUpdateButtonListener(e -> handleUpdateJob());
        view.addDeleteButtonListener(e -> handleDeleteJob());
        view.addClearButtonListener(e -> handleClearForm());
        
        // Search and filter listeners
        view.addSearchFieldListener(e -> handleSearch());
        view.addStatusFilterListener(e -> handleFilterByStatus());
        view.addFilterButtonListener(e -> handleFilterButton());
        view.addClearSearchButtonListener(e -> handleClearSearch());
        
        // Remove real-time search - now only trigger on button or enter
        // view.getSearchField().getDocument().addDocumentListener(...) - commented out
        
        // Table selection listener
        view.addTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleTableSelection();
            }
        });
    }

    private void loadJobApplications() {
        try {
            jobApplications = jobApplicationService.findByUserId(currentUser.getId());
            refreshTable();
        } catch (Exception e) {
            logger.error("Failed to load job applications", e);
            view.showErrorMessage("Failed to load job applications: " + e.getMessage());
        }
    }

    private void refreshTable() {
        view.clearTable();
        if (jobApplications == null || jobApplications.isEmpty()) {
            return;
        }

        for (JobApplication job : jobApplications) {
            view.addTableRow(new Object[]{
                job.getId(),
                job.getCompanyName(),
                job.getJobTitle(),
                DateUtils.formatForDisplay(job.getApplicationDate().toLocalDate()),
                job.getStatus() != null ? job.getStatus().getDisplayName() : JobStatus.APPLIED.getDisplayName()
            });
        }
    }

    private void handleAddJob() {
        if (!validateForm()) {
            return;
        }

        try {
            JobApplication newJob = createJobApplicationFromForm();
            newJob.setUserId(currentUser.getId());
            newJob.setApplicationDate(LocalDateTime.now());
            
            JobApplication savedJob = jobApplicationService.insert(newJob);
            
            if (savedJob != null) {
                view.showSuccessMessage("Job application added successfully!");
                loadJobApplications();
                view.clearForm();
                view.focusCompanyField();
            }
            
        } catch (Exception e) {
            logger.error("Failed to add job application", e);
            view.showErrorMessage("Failed to add job application: " + e.getMessage());
        }
    }

    private void handleUpdateJob() {
        if (selectedJobApplication == null) {
            view.showValidationError("Please select a job from the table to update.");
            return;
        }
        
        if (!validateForm()) {
            return;
        }

        try {
            updateJobApplicationFromForm(selectedJobApplication);
            
            JobApplication updatedJob = jobApplicationService.update(selectedJobApplication);
            
            if (updatedJob != null) {
                view.showSuccessMessage("Job application updated successfully!");
                loadJobApplications();
                view.clearForm();
                selectedJobApplication = null;
            }
            
        } catch (Exception e) {
            logger.error("Failed to update job application", e);
            view.showErrorMessage("Failed to update job application: " + e.getMessage());
        }
    }

    private void handleDeleteJob() {
        if (selectedJobApplication == null) {
            view.showValidationError("Please select a job from the table to delete.");
            return;
        }

        String message = String.format("Are you sure you want to delete the job application for:\n%s - %s?",
                selectedJobApplication.getCompanyName(), selectedJobApplication.getJobTitle());
                
        if (view.showConfirmDialog(message)) {
            try {
                int deleted = jobApplicationService.delete(selectedJobApplication.getId());
                
                if (deleted == 1) {
                    view.showSuccessMessage("Job application deleted successfully!");
                    loadJobApplications();
                    view.clearForm();
                    selectedJobApplication = null;
                }
                
            } catch (Exception e) {
                logger.error("Failed to delete job application", e);
                view.showErrorMessage("Failed to delete job application: " + e.getMessage());
            }
        }
    }

    private void handleClearForm() {
        view.clearForm();
        selectedJobApplication = null;
    }

    private void handleSearch() {
        String keyword = view.getSearchField().getText().trim();
        String statusFilter = Objects.requireNonNull(view.getStatusFilterComboBox().getSelectedItem()).toString();
        
        try {
            List<JobApplication> filteredJobs;
            
            if (!keyword.isEmpty()) {
                filteredJobs = jobApplicationService.searchJobApplications(currentUser.getId(), keyword);
            } else {
                filteredJobs = jobApplicationService.findByUserId(currentUser.getId());
            }
            
            // Apply status filter
            if (!statusFilter.equals("All Status")) {
                filteredJobs = jobApplicationService.filterByStatus(currentUser.getId(), statusFilter);
                
                // Apply keyword filter on status-filtered results if needed
                if (!keyword.isEmpty()) {
                    final String finalKeyword = keyword.toLowerCase();
                    filteredJobs = filteredJobs.stream()
                            .filter(job -> job.getCompanyName().toLowerCase().contains(finalKeyword) ||
                                         job.getJobTitle().toLowerCase().contains(finalKeyword))
                            .collect(java.util.stream.Collectors.toList());
                }
            }
            
            jobApplications = filteredJobs;
            refreshTable();
            
        } catch (Exception e) {
            logger.error("Failed to search job applications", e);
            view.showErrorMessage("Search failed: " + e.getMessage());
        }
    }

    private void handleFilterByStatus() {
        handleSearch(); // Reuse search logic which handles both keyword and status filtering
    }

    private void handleFilterButton() {
        handleSearch(); // Apply current search and filter settings
    }

    private void handleClearSearch() {
        view.clearSearchAndFilter();
        loadJobApplications(); // Reload all data without filters
    }

    private void handleTableSelection() {
        int selectedRow = view.getJobTable().getSelectedRow();
        if (selectedRow >= 0 && selectedRow < jobApplications.size()) {
            selectedJobApplication = jobApplications.get(selectedRow);
            populateFormWithSelectedJob(selectedJobApplication);
        }
    }

    private boolean validateForm() {
        if (!view.isFormValid()) {
            view.showValidationError("Please fill in all required fields:\n- Company\n- Job Title");
            return false;
        }
        
        String company = view.getCompanyField().getText().trim();
        String title = view.getTitleField().getText().trim();

        if (company.isEmpty()) {
            view.showValidationError("Company name is required.");
            view.focusCompanyField();
            return false;
        }
        
        if (title.isEmpty()) {
            view.showValidationError("Job title is required.");
            view.focusTitleField();
            return false;
        }
        
        return true;
    }

    private JobApplication createJobApplicationFromForm() {
        JobApplication job = new JobApplication();
        updateJobApplicationFromForm(job);
        return job;
    }

    private void updateJobApplicationFromForm(JobApplication job) {
        job.setCustomCompany(view.getCompanyField().getText().trim());
        job.setCustomTitle(view.getTitleField().getText().trim());
        job.setCustomUrl(view.getUrlField().getText().trim());
        job.setCustomDescription(view.getDescriptionArea().getText().trim());
        job.setNotes(view.getNotesArea().getText().trim());

        // Handle status
        setJobStatus(job);
    }
    
    private void setJobStatus(JobApplication job) {
        String statusDisplayName = Objects.requireNonNull(view.getStatusComboBox().getSelectedItem()).toString();
        
        // Convert display name back to enum
        JobStatus status = null;
        for (JobStatus jobStatus : JobStatus.values()) {
            if (jobStatus.getDisplayName().equals(statusDisplayName)) {
                status = jobStatus;
                break;
            }
        }
        
        // Set status or default to APPLIED
        job.setStatus(status != null ? status : JobStatus.APPLIED);
    }

    private void populateFormWithSelectedJob(JobApplication job) {
        view.getCompanyField().setText(job.getCompanyName() != null ? job.getCompanyName() : "");
        view.getTitleField().setText(job.getJobTitle() != null ? job.getJobTitle() : "");
        view.getUrlField().setText(job.getJobUrl() != null ? job.getJobUrl() : "");
        view.getDescriptionArea().setText(job.getJobDescription() != null ? job.getJobDescription() : "");
        view.getNotesArea().setText(job.getNotes() != null ? job.getNotes() : "");
        
        // Set status
        if (job.getStatus() != null) {
            view.getStatusComboBox().setSelectedItem(job.getStatus().getDisplayName());
        }
    }
}
