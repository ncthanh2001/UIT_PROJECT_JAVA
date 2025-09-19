package org.smart_job.controller.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.common.Response;
import org.smart_job.controller.MainController;
import org.smart_job.entity.User;
import org.smart_job.service.UserService;
import org.smart_job.service.impl.UserServiceImpl;
import org.smart_job.util.Extensions;
import org.smart_job.view.auth.RegisterView;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RegisterController {
    private final RegisterView view;
    private final UserService userService;
    private static final Logger logger = LogManager.getLogger(RegisterController.class);

    public RegisterController(RegisterView view) {
        this.view = view;
        this.userService = new UserServiceImpl();
        initListeners();
    }

    private void initListeners() {
        view.getRegisterButton().addActionListener(e -> handleRegister());
        view.getCancelButton().addActionListener(e -> navigateToLogin());
    }

    // --- Core logic ---
    private void handleRegister() {
        Response<User> validation = validateUserInput();
        if (!validation.isSuccess()) {
            showError(validation.getMessage());
            return;
        }

        User user = validation.getData();
        Response<User> registerResponse = userService.register(user);

        if (registerResponse.isSuccess()) {
            showSuccess("Registration successful! Welcome, " + user.getFirstName() + "!");
            clearForm();
            navigateToLogin();
        } else {
            showError("Registration failed: " + registerResponse.getMessage());
        }
    }

    private Response<User> validateUserInput() {
        String firstName = view.getFirstNameField().getText().trim();
        String lastName = view.getLastNameField().getText().trim();
        String email = view.getEmailField().getText().trim();
        String password = new String(view.getPasswordField().getPassword());
        String confirmPassword = new String(view.getConfirmPasswordField().getPassword());
        String country = (String) view.getCountryComboBox().getSelectedItem();
        Date dob = view.getDobChooser().getDate();

        if (firstName.isEmpty()) return Response.fail("First name is required");
        if (lastName.isEmpty()) return Response.fail("Last name is required");
        if (email.isEmpty() || !Extensions.isValidEmail(email)) return Response.fail("Invalid email");
        if (password.isEmpty() || password.length() < 6) return Response.fail("Password must be at least 6 characters");
        if (!password.equals(confirmPassword)) return Response.fail("Passwords do not match");
        if (country == null || country.isEmpty()) return Response.fail("Please select a country");
        if (dob == null) return Response.fail("Please select date of birth");

        LocalDate dobLocal = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (dobLocal.isAfter(LocalDate.now().minusYears(16))) return Response.fail("You must be at least 16 years old");

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setCountry(country);
        user.setDob(dobLocal);

        return Response.ok(user);
    }

    // --- Navigation ---
    private void navigateToLogin() {
        MainController.getInstance().showLogin();
        view.dispose();
    }

    // --- UI Helpers ---
    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(view, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        view.getFirstNameField().setText("");
        view.getLastNameField().setText("");
        view.getEmailField().setText("");
        view.getPasswordField().setText("");
        view.getConfirmPasswordField().setText("");
        view.getCountryComboBox().setSelectedIndex(0);
        view.getDobChooser().setDate(new Date());
    }
}
