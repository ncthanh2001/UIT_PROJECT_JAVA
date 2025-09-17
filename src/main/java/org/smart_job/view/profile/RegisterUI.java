package org.smart_job.view.profile;

import org.smart_job.entity.User;
import org.smart_job.service.UserService;
import org.smart_job.service.impl.UserServiceImpl;
import org.smart_job.common.Response;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class RegisterUI extends JFrame {
    private final UserService userService = new UserServiceImpl();

    private JTextField txtFirstName, txtLastName, txtEmail, txtAvatar, txtCountry, txtDob;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;

    public RegisterUI() {
        setTitle("User Registration");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        // Main panel với padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Tiêu đề
        JLabel titleLabel = new JLabel("CREATE ACCOUNT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Panel input với GridBagLayout để căn chỉnh tốt hơn
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Khởi tạo các trường nhập liệu
        txtFirstName = createTextField();
        txtLastName = createTextField();
        txtEmail = createTextField();
        txtPassword = new JPasswordField();
        txtAvatar = createTextField();
        txtCountry = createTextField();
        txtDob = createTextField();
        txtDob.setText("2000-01-01");

        // Thêm các trường vào panel
        addFormField(inputPanel, gbc, "First Name:", txtFirstName, 0);
        addFormField(inputPanel, gbc, "Last Name:", txtLastName, 1);
        addFormField(inputPanel, gbc, "Email:", txtEmail, 2);
        addFormField(inputPanel, gbc, "Password:", txtPassword, 3);
        addFormField(inputPanel, gbc, "Avatar URL:", txtAvatar, 4);
        addFormField(inputPanel, gbc, "Country:", txtCountry, 5);
        addFormField(inputPanel, gbc, "Date of Birth (yyyy-MM-dd):", txtDob, 6);

        // Panel button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnBack = new JButton("Back");
        btnBack.setBackground(new Color(240, 240, 240));
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> dispose());

        btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(0, 102, 204));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.addActionListener(e -> registerUser());

        buttonPanel.add(btnBack);
        buttonPanel.add(btnRegister);

        // Thêm các component vào main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(inputPanel), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 30));
        return textField;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int gridy) {
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weightx = 0.3;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(field, gbc);
    }

    private void registerUser() {
        try {
            // Validation cơ bản
            if (!validateForm()) {
                return;
            }

            User user = new User();
            user.setFirstName(txtFirstName.getText().trim());
            user.setLastName(txtLastName.getText().trim());
            user.setEmail(txtEmail.getText().trim());
            user.setPassword(new String(txtPassword.getPassword()));
            user.setAvatar(txtAvatar.getText().trim());
            user.setCountry(txtCountry.getText().trim());
            user.setDob(LocalDate.parse(txtDob.getText().trim()));

            Response<User> res = userService.register(user);
            if (res.isSuccess()) {
                JOptionPane.showMessageDialog(this,
                        "Registration successful!\nWelcome, " + user.getFirstName() + "!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                dispose(); // Đóng cửa sổ sau khi đăng ký thành công
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: " + res.getMessage(),
                        "Registration Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid input: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateForm() {
        if (txtFirstName.getText().trim().isEmpty()) {
            showValidationError("Please enter your first name.");
            return false;
        }
        if (txtLastName.getText().trim().isEmpty()) {
            showValidationError("Please enter your last name.");
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            showValidationError("Please enter your email address.");
            return false;
        }
        if (txtPassword.getPassword().length == 0) {
            showValidationError("Please enter a password.");
            return false;
        }
        if (txtDob.getText().trim().isEmpty()) {
            showValidationError("Please enter your date of birth.");
            return false;
        }
        return true;
    }

    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.WARNING_MESSAGE);
    }

    private void clearForm() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtAvatar.setText("");
        txtCountry.setText("");
        txtDob.setText("2000-01-01");
    }
}