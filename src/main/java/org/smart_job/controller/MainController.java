package org.smart_job.controller;

import lombok.Getter;
import org.smart_job.controller.auth.LoginController;
import org.smart_job.controller.auth.RegisterController;
import org.smart_job.session.UserSession;
import org.smart_job.view.BaseLayoutView;
import org.smart_job.view.CVAnalysis.CVAnalysisContentPanel;
import org.smart_job.view.auth.LoginView;
import org.smart_job.view.auth.RegisterView;
import org.smart_job.view.dashboard.DashboardContentPanel;
import org.smart_job.view.jobs.JobTrackerContentPanel;
import org.smart_job.view.profile.ProfileContentPanel;
import org.smart_job.service.impl.UserServiceImpl;

public class MainController {
    @Getter
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
        view.setVisible(false);
//        showDashboard(); // Default show dashboard
        showLogin(); // Show login
    }

    private void registerEvents() {
        view.getDashboardButton().addActionListener(e -> showDashboard());
        view.getCvAnalysisButton().addActionListener(e -> showCVAnalysis());
        view.getJobTrackerButton().addActionListener(e -> showJobTracker());
        view.getProfileButton().addActionListener(e -> showProfile());
        view.getLogoutButton().addActionListener(e -> {
            logout();
            showLogin();
        });
    }

    // --- Navigation Methods ---
    public void showDashboard() {
        view.setVisible(true);
        DashboardContentPanel panel = new DashboardContentPanel();
        DashboardController dashboardController = new DashboardController(panel);

        // Set up navigation handlers for dashboard buttons
        dashboardController.setNavigateToJobTracker(() -> showJobTracker());
        dashboardController.setNavigateToCVAnalysis(() -> showCVAnalysis());

        view.setContent(panel);
    }

    public void showCVAnalysis() {
        view.setVisible(true);

        CVAnalysisContentPanel panel = new CVAnalysisContentPanel();
        new CVAnalysisController(panel);
        view.setContent(panel);
    }

    public void showJobTracker() {
        view.setVisible(true);

        JobTrackerContentPanel panel = new JobTrackerContentPanel();
        new JobsTrackerController(panel);
        view.setContent(panel);
    }

    public void showProfile() {
        view.setVisible(true);

        ProfileContentPanel panel = new ProfileContentPanel();
        var currentUser = UserSession.getInstance().getCurrentUser();
        var userService = new UserServiceImpl();

        new ProfileController(panel, userService, currentUser);
        view.setContent(panel);
    }


    public void showLogin() {
        view.setVisible(false);

        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);

    }

    public void showRegister() {
        RegisterView registerView = new RegisterView();
        new RegisterController(registerView);
        registerView.setVisible(true);
    }

    private void logout() {
        UserSession.getInstance().clear();
    }
}