package org.smart_job.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.view.LoginView;
import org.smart_job.view.SetUpProfileView;

import javax.swing.*;

public class LoginController {
    private final LoginView view;
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    public LoginController(LoginView view) {
        this.view = view;
        initListeners();
    }

    private void initListeners() {
        view.getLoginButton().addActionListener(e -> {
            String username = view.getUsernameField().getText().trim();
            String password = new String(view.getPasswordField().getPassword());
            JLabel messageLabel = view.getMessageLabel();
            System.out.println("[LoginController] Username: " + username);
            System.out.println("[LoginController] Password: " + password);
            System.out.println("[LoginController] Login button clicked");
            try {
                // Add logic

                messageLabel.setText("Login successful!");
                logger.info("User {} logged in successfully.", username);

                SetUpProfileView setUpProfileView = new SetUpProfileView();
                new SetUpProfileController(setUpProfileView);
                setUpProfileView.setVisible(true);

                view.dispose();
            } catch (Exception ex) {
                System.out.println("[LoginController] Error during login: " + ex.getMessage());
                messageLabel.setText("An error occurred during login. Please try again.");
                JOptionPane.showMessageDialog(view, "An error occurred during login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                logger.error("Error during login", ex);
            }
        });
    }
}
