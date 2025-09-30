package org.smart_job.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.entity.Job;
import org.smart_job.entity.User;
import org.smart_job.service.JobApplicationService;
import org.smart_job.service.JobService;
import org.smart_job.service.impl.JobApplicationServiceImpl;
import org.smart_job.service.impl.JobServiceImpl;
import org.smart_job.session.UserSession;
import org.smart_job.view.dashboard.DashboardContentPanel;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class DashboardController {
    private final DashboardContentPanel view;
    private final JobApplicationService jobApplicationService;
    private final JobService jobService;
    private static final Logger logger = LogManager.getLogger(DashboardController.class);

    // Navigation handlers - to be set by main controller
    private Runnable navigateToJobTracker;
    private Runnable navigateToCVAnalysis;

    public DashboardController(DashboardContentPanel view) {
        this.view = view;
        this.jobApplicationService = new JobApplicationServiceImpl();
        this.jobService = new JobServiceImpl();
        init();
    }

    private void init() {
        // Load and display real dashboard data
        loadDashboardData();

        // Set up navigation listeners
        setupNavigationListeners();

        // Load job suggestions
        loadJobSuggestions();
    }

    private void setupNavigationListeners() {
        view.setNavigationListeners(
                // Add Job - Navigate to Job Tracker
                e -> {
                    if (navigateToJobTracker != null) {
                        navigateToJobTracker.run();
                    } else {
                        logger.warn("Navigation to Job Tracker not set");
                        JOptionPane.showMessageDialog(view,
                                "Chức năng đang được phát triển",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                },

                // Analyze CV - Navigate to CV Analysis
                e -> {
                    if (navigateToCVAnalysis != null) {
                        navigateToCVAnalysis.run();
                    } else {
                        logger.warn("Navigation to CV Analysis not set");
                        JOptionPane.showMessageDialog(view,
                                "Chức năng phân tích CV đang được phát triển",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                },

                // View All Applications - Navigate to Job Tracker
                e -> {
                    if (navigateToJobTracker != null) {
                        navigateToJobTracker.run();
                    } else {
                        logger.warn("Navigation to Job Tracker not set");
                        JOptionPane.showMessageDialog(view,
                                "Chức năng xem tất cả job apply đang được phát triển",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
        );
    }

    private void loadJobSuggestions() {
        try {
            // Get job suggestions using JobService pagination method
            List<Job> suggestedJobs = jobService.findJobs("", "", "", 1, 4);

            // Update view with real job data
            view.updateJobSuggestions(suggestedJobs);

            logger.info("Loaded {} job suggestions", suggestedJobs != null ? suggestedJobs.size() : 0);
        } catch (Exception e) {
            logger.error("Error loading job suggestions: ", e);
            // Keep sample data if error occurs
        }
    }

    private void loadDashboardData() {
        try {
            User currentUser = UserSession.getInstance().getCurrentUser();
            if (currentUser == null) {
                logger.warn("No user logged in, cannot load dashboard data");
                showDefaultData();
                return;
            }

            logger.debug("Loading dashboard data for user: {}", currentUser.getEmail());

            // Get dashboard statistics
            Map<String, Object> stats = jobApplicationService.getDashboardStats(currentUser.getId());

            // Get user profile info (using available fields from User entity)
            String userName = currentUser.getFullName() != null ?
                    currentUser.getFullName() : "User";

            // For now, use placeholder values for fields not in User entity
            // These could be retrieved from separate CV, Skills, Profile tables later
            String desiredPosition = "Chưa cập nhật"; // Could be from user profile table
            String cvFileName = "Chưa upload";        // Could be from CV table
            String skills = "Chưa cập nhật";          // Could be from user_skills table

            // Update view with real data
            view.updateDashboardData(
                    userName,
                    desiredPosition,
                    cvFileName,
                    skills,
                    (Integer) stats.get("totalApplications"),
                    (Double) stats.get("responseRate"),
                    (Integer) stats.get("pendingResponses"),
                    (Integer) stats.get("interviewInvites")
            );

            logger.info("Dashboard data loaded successfully for user: {}", currentUser.getEmail());
        } catch (Exception e) {
            logger.error("Error loading dashboard data: ", e);
            showDefaultData();
        }
    }

    private void showDefaultData() {
        // Show default data if error occurs or no user logged in
        view.updateDashboardData(
                "User",
                "Chưa cập nhật",
                "Chưa upload",
                "Chưa cập nhật",
                0, 0.0, 0, 0
        );
    }

    private String extractFileName(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "Chưa upload";
        }
        int lastSlashIndex = filePath.lastIndexOf('/');
        int lastBackslashIndex = filePath.lastIndexOf('\\');
        int lastSeparatorIndex = Math.max(lastSlashIndex, lastBackslashIndex);

        if (lastSeparatorIndex >= 0 && lastSeparatorIndex < filePath.length() - 1) {
            return filePath.substring(lastSeparatorIndex + 1);
        }
        return filePath;
    }

    public void refreshDashboard() {
        loadDashboardData();
        loadJobSuggestions();
    }

    // Methods to set navigation handlers from main controller
    public void setNavigateToJobTracker(Runnable navigateToJobTracker) {
        this.navigateToJobTracker = navigateToJobTracker;
    }

    public void setNavigateToCVAnalysis(Runnable navigateToCVAnalysis) {
        this.navigateToCVAnalysis = navigateToCVAnalysis;
    }
}
