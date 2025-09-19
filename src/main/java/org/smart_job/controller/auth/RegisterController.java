package org.smart_job.controller.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.dto.Response;
import org.smart_job.dto.auth.RegisterUserDto;
import org.smart_job.entity.UserEntity;
import org.smart_job.service.UserService;
import org.smart_job.view.auth.LoginView;
import org.smart_job.view.auth.RegisterView;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class RegisterController {
    private static final Logger logger = LogManager.getLogger(RegisterController.class);
    private final LoginView loginView;
    private final RegisterView  registerView;
    private final UserService userService;

    public RegisterController(LoginView loginView, RegisterView registerView, UserService userService) {
        this.loginView = loginView;
        this.registerView = registerView;

        this.userService = userService;

        initListeners();
    }

    private void initListeners() {
        registerView.getCancelButton().addActionListener(e -> {
            registerView.setVisible(false);
            loginView.setVisible(true);
        });

        registerView.getRegisterButton().addActionListener(e -> {
            validateAndBuildUser().ifPresent(user -> {
                // Gọi service để lưu DB
                Response<UserEntity> response = userService.register(user);
                if (response.isSuccess()) {
                    registerView.getMessageLabel().setForeground(Color.GREEN);
                    registerView.getMessageLabel().setText("Registration successful!");

                    // When register success
                    loginView.setVisible(true);
                    registerView.setVisible(false);
                } else {
                    showError(response.getMessage());
                }
            });
        });
    }


    private Optional<RegisterUserDto> validateAndBuildUser() {
        String firstName = registerView.getFirstNameField().getText().trim();
        String lastName = registerView.getLastNameField().getText().trim();
        String email = registerView.getEmailField().getText().trim();
        String password = new String(registerView.getPasswordField().getPassword());
        String confirmPassword = new String(registerView.getConfirmPasswordField().getPassword());
        String country = (String) registerView.getCountryComboBox().getSelectedItem();
        Date dob = registerView.getDobChooser().getDate();

        // --- Validation ---
        if (firstName.isEmpty()) {
            showError("First name is required");
            return Optional.empty();
        }

        if (lastName.isEmpty()) {
            showError("Last name is required");
            return Optional.empty();
        }

        if (email.isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError("Invalid email");
            return Optional.empty();
        }

        if (password.isEmpty() || password.length() < 6) {
            showError("Password must be at least 6 characters");
            return Optional.empty();
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match");
            return Optional.empty();
        }

        if (country == null || country.isEmpty()) {
            showError("Please select a country");
            return Optional.empty();
        }

        if (dob == null) {
            showError("Please select date of birth");
            return Optional.empty();
        }

        LocalDate dobLocal = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (dobLocal.isAfter(LocalDate.now().minusYears(16))) {
            showError("You must be at least 16 years old");
            return Optional.empty();
        }

        // --- Build Dto ---
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setPassword(password);
        userDto.setConfirmPassword(confirmPassword);
        userDto.setCountry(country);
        userDto.setDob(dobLocal);

        return Optional.of(userDto);
    }

    private void showError(String message) {
        registerView.getMessageLabel().setForeground(Color.RED);
        registerView.getMessageLabel().setText(message);
    }

}
