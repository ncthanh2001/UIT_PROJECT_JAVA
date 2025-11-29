package org.smart_job.controller.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.common.Response;
import org.smart_job.controller.MainController;
import org.smart_job.entity.User;
import org.smart_job.service.UserService;
import org.smart_job.service.impl.UserServiceImpl;
import org.smart_job.session.UserSession;
import org.smart_job.util.Extensions;
import org.smart_job.view.BaseLayoutView;
import org.smart_job.view.auth.LoginView;

import javax.swing.*;

public class LoginController {
    private final LoginView view;
    private final UserService userService;
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    public LoginController(LoginView view) {
        this.view = view;
        this.userService = new UserServiceImpl();
        initListeners();
    }

    private void initListeners() {
        view.getLoginButton().addActionListener(e -> handleLogin());
        view.getRegisterButton().addActionListener(e -> navigateToRegister());
    }

    // --- Core logic ---
    private void handleLogin() {
        Response<User> validation = validateInput();
        if (!validation.isSuccess()) {
            showError(validation.getMessage());
            return;
        }

        User loginDto = validation.getData();
        Response<User> loginResponse = userService.login(loginDto);

        if (loginResponse.isSuccess()) {
            User loggedUser = loginResponse.getData();

            // Lưu thông tin user đã login (session / singleton / context)
            UserSession.getInstance().setCurrentUser(loggedUser);



            if (UserSession.getInstance().isLoggedIn()) {
                User current = UserSession.getInstance().getCurrentUser();
                System.out.println("Logged in user: " + current.getEmail());
            }

            view.dispose(); // Đóng Login View

            // Chuyển tới setup profile
            MainController.getInstance().showDashboard();
        } else {
            showError("Login failed: " + loginResponse.getMessage());
        }
    }

    private Response<User> validateInput() {
        String email = view.getEmailField().getText().trim();
        String password = new String(view.getPasswordField().getPassword());

        if (email.isEmpty() || !Extensions.isValidEmail(email)) {
            return Response.fail("Invalid email");
        }

        if (password.isEmpty() || password.length() < 6) {
            return Response.fail("Password must be at least 6 characters");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return Response.ok(user);
    }

    // --- Navigation ---
    private void navigateToRegister() {
        MainController.getInstance().showRegister();
        view.dispose();
    }

    // --- UI Helpers ---
    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(view, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
