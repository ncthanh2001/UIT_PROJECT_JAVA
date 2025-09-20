package org.smart_job.view.auth;

import com.toedter.calendar.JDateChooser;
import lombok.Getter;
import org.smart_job.entity.User;
import org.smart_job.service.UserService;
import org.smart_job.service.impl.UserServiceImpl;
import org.smart_job.common.Response;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

@Getter
public class RegisterView extends JFrame {
    private final UserService userService = new UserServiceImpl();

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> countryComboBox;
    private JDateChooser dobChooser;
//    private JComboBox<UserRole> roleComboBox;
    private JButton registerButton;
    private JButton cancelButton;

    public RegisterView() {
        setTitle("User Registration");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        int row = 0;

        // Title
        JLabel titleLabel = new JLabel("User Registration", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 144, 255));
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // First name
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("First Name:"), gbc);
        firstNameField = new JTextField(20);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(firstNameField, gbc);
        row++;

        // Last name
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Last Name:"), gbc);
        lastNameField = new JTextField(20);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(lastNameField, gbc);
        row++;

        // Email
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(emailField, gbc);
        row++;

        // Password
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordField, gbc);
        row++;

        // Confirm password
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Confirm Password:"), gbc);
        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(confirmPasswordField, gbc);
        row++;

        // Country
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Country:"), gbc);
        String[] countries = Locale.getISOCountries();
        String[] countryNames = new String[countries.length];
        for (int i = 0; i < countries.length; i++) {
            Locale locale = new Locale("", countries[i]);
            countryNames[i] = locale.getDisplayCountry();
        }
        countryComboBox = new JComboBox<>(countryNames);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(countryComboBox, gbc);
        row++;

        // Date of birth (calendar)
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Date of Birth:"), gbc);

        dobChooser = new JDateChooser();
        dobChooser.setDateFormatString("yyyy-MM-dd");
        dobChooser.setPreferredSize(new Dimension(200, 30));
        dobChooser.setDate(new Date());

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(dobChooser, gbc);
        row++;

        // Role
//        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST;
//        mainPanel.add(new JLabel("Role:"), gbc);
//
//        roleComboBox = new JComboBox<>(UserRole.values()); // load từ enum
//        roleComboBox.setSelectedItem(UserRole.USER); // default USER
//        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
//        mainPanel.add(roleComboBox, gbc);
//        row++;

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(30, 144, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(100, 30));

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

}