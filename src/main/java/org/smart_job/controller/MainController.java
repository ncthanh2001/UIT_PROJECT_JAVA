package org.smart_job.controller;

import org.smart_job.view.BaseLayoutView;
import org.smart_job.view.CVAnalysis.CVAnalysisContentPanel;
import org.smart_job.view.LoginView;
import org.smart_job.view.dashboard.DashboardContentPanel;
import org.smart_job.view.jobs.JobTrackerContentPanel;
import org.smart_job.view.profile.ProfileContentPanel;

public class MainController {
    private static MainController instance;
    private final BaseLayoutView view;

    private MainController(BaseLayoutView view) {
        this.view = view;

        registerEvents();
    }

    public static MainController init(BaseLayoutView view) {
        if (instance == null) {
            instance = new MainController(view);
        }
        return instance;
    }

    public void start() {
        view.setVisible(true);
        // TODO: Check user exist then show view
        showDashboard(); // Default show dashboard
    }

    private void registerEvents() {
        view.getDashboardButton().addActionListener(e -> showDashboard());
        view.getCvAnalysisButton().addActionListener(e -> showCVAnalysis());
        view.getJobTrackerButton().addActionListener(e -> showJobTracker());
        view.getProfileButton().addActionListener(e -> showProfile());
        view.getLoginButton().addActionListener(e -> showLogin());
    }

    // --- Navigation Methods ---
    public void showDashboard() {
        DashboardContentPanel panel = new DashboardContentPanel();
        new DashboardController(panel);
        view.setContent(panel);
    }

    public void showCVAnalysis() {
        CVAnalysisContentPanel panel = new CVAnalysisContentPanel();
        new CVAnalysisController(panel);
        view.setContent(panel);
    }

    public void showJobTracker() {
        JobTrackerContentPanel panel = new JobTrackerContentPanel();
        new JobsTrackerController(panel);
        view.setContent(panel);
    }

    public void showProfile() {
        ProfileContentPanel panel = new ProfileContentPanel();
        new ProfileController(panel);
        view.setContent(panel);
    }

    public void showLogin() {
        LoginView view = new LoginView();
        new LoginController(view);
        view.setVisible(true);

        this.view.dispose();
    }

}
