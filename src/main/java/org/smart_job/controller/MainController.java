package org.smart_job.controller;

import org.smart_job.controller.auth.LoginController;
import org.smart_job.controller.auth.RegisterController;
import org.smart_job.service.UserService;
import org.smart_job.view.BaseLayoutView;
import org.smart_job.view.CVAnalysis.CVAnalysisContentPanel;
import org.smart_job.view.auth.LoginView;
import org.smart_job.view.auth.RegisterView;
import org.smart_job.view.dashboard.DashboardContentPanel;
import org.smart_job.view.jobs.JobTrackerContentPanel;
import org.smart_job.view.profile.ProfileContentPanel;

public class MainController {
    private static MainController instance;
    private final BaseLayoutView view;
    private final UserService userService;

    private MainController(BaseLayoutView view, UserService userService) {
        this.view = view;
        this.userService = userService;
        registerEvents();
    }

    public static MainController init(BaseLayoutView view, UserService userService) {
        if (instance == null) {
            instance = new MainController(view, userService);
        }
        return instance;
    }

    // DI singleton pattern
    public static MainController getInstance() {
        return instance;
    }

    public void bootstrap() {
        view.setVisible(true);
        showLogin(); // Default show login view when app is bootstrap
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

        this.view.setVisible(true);
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
        LoginView loginView = new LoginView();
        RegisterView registerView = new RegisterView();
        new LoginController(loginView, registerView,userService);
        new RegisterController(loginView,registerView,userService);
        loginView.setVisible(true);

        this.view.setVisible(false);
    }

}
