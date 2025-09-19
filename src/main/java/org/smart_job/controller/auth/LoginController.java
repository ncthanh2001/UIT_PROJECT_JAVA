package org.smart_job.controller.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.controller.MainController;
import org.smart_job.dto.Response;
import org.smart_job.dto.auth.LoginUserDto;
import org.smart_job.dto.auth.RegisterUserDto;
import org.smart_job.service.UserService;
import org.smart_job.util.Extensions;
import org.smart_job.view.auth.LoginView;
import org.smart_job.view.auth.RegisterView;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;
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

            validateAndBuildUser().ifPresent(user -> {
                // Call service to authenticate
                Response response = userService.login(user);
                if (response.isSuccess()) {
                    loginView.getMessageLabel().setForeground(Color.GREEN);
                    loginView.getMessageLabel().setText("Login successful!");

                    // When login success
                    loginView.dispose();
                    registerView.dispose();
                    MainController.getInstance().showDashboard();
                } else {
                    showError(response.getMessage());
                }
            });

        });
    }

    private Optional<LoginUserDto> validateAndBuildUser() {
        String email = loginView.getEmailField().getText().trim();
        String password = new String(loginView.getPasswordField().getPassword());

        if (email.isEmpty() || !Extensions.isValidEmail(email)) {
            showError("Invalid email");
            return Optional.empty();
        }

        if (password.isEmpty() || password.length() < 6) {
            showError("Password must be at least 6 characters");
            return Optional.empty();
        }

        // --- Build Dto ---
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(email);
        loginUserDto.setPassword(password);

        return Optional.of(loginUserDto);
    }

    private void showError(String message) {
        loginView.getMessageLabel().setForeground(Color.RED);
        loginView.getMessageLabel().setText(message);
    }

}
