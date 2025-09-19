package org.smart_job.controller.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.controller.MainController;
import org.smart_job.service.UserService;
import org.smart_job.view.auth.LoginView;
import org.smart_job.view.auth.RegisterView;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    private final LoginView loginView;
    private final RegisterView  registerView;
    private final UserService userService;

    public LoginController(LoginView loginView, RegisterView registerView,UserService userService) {
        this.loginView = loginView;
        this.userService = userService;
        this.registerView = registerView;
        initListeners();
    }

    private void initListeners() {

        loginView.getRegisterButton().addActionListener(e -> {
            loginView.setVisible(false);
            registerView.setVisible(true);
        });

        loginView.getLoginButton().addActionListener(e -> {
            String email = loginView.getEmailField().getText().trim();
            String password = new String(loginView.getPasswordField().getPassword());
            JLabel messageLabel = loginView.getMessageLabel();
            System.out.println("[LoginController] Email: " + email);
            System.out.println("[LoginController] Password: " + password);
            System.out.println("[LoginController] Login button clicked");
            try {
                if (!isValidEmail(email)) {
                    messageLabel.setText("Email is not valid!");
                    return;
                } else if (password.isEmpty()) {
                    messageLabel.setText("Password is required!");
                    return;

                } else {
                    messageLabel.setForeground(Color.GREEN);
                    messageLabel.setText("Login successful!.");
                    // TODO: call service check user



                    // When login success
                    MainController.getInstance().showDashboard();
                    loginView.dispose();
                }

                messageLabel.setText("Login successful!");
                logger.info("User {} logged in successfully.", email);
            } catch (Exception ex) {
                System.out.println("[LoginController] Error during login: " + ex.getMessage());
                messageLabel.setText("An error occurred during login. Please try again.");
                JOptionPane.showMessageDialog(loginView, "An error occurred during login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                logger.error("Error during login", ex);
            }
        });
    }

    // Regex validate email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}
