package org.smart_job.view;

import org.smart_job.MainApp;
import org.smart_job.service.UserService;
import org.smart_job.service.impl.UserServiceImpl;
import org.smart_job.common.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterFrame extends JPanel {
    private MainApp mainApp;

    // Các hằng số để tùy chỉnh vị trí và khoảng cách
    private static final int TITLE_X = 0;
    private static final int TITLE_Y = 0;
    private static final int EMAIL_LABEL_X = 0;
    private static final int EMAIL_LABEL_Y = 1;
    private static final int EMAIL_FIELD_X = 0;
    private static final int EMAIL_FIELD_Y = 2;
    private static final int PASSWORD_LABEL_X = 0;
    private static final int PASSWORD_LABEL_Y = 3;
    private static final int PASSWORD_FIELD_X = 0;
    private static final int PASSWORD_FIELD_Y = 4;
    private static final int CONFIRM_PASSWORD_LABEL_X = 0;
    private static final int CONFIRM_PASSWORD_LABEL_Y = 5;
    private static final int CONFIRM_PASSWORD_FIELD_X = 0;
    private static final int CONFIRM_PASSWORD_FIELD_Y = 6;
    private static final int REGISTER_BUTTON_X = 0;
    private static final int REGISTER_BUTTON_Y = 7;
    private static final int LOGIN_LABEL_X = 0;
    private static final int LOGIN_LABEL_Y = 8;
    private static final Insets DEFAULT_INSETS = new Insets(8, 8, 8, 8);
    private static final int FIELD_WIDTH = 20;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 40;

    public RegisterFrame(MainApp mainApp) {
        this.mainApp = mainApp;
        setBackground(Color.WHITE);
        setLayout(new GridLayout(1, 2, 10, 10)); // Chia đôi trang: trái và phải

        // Phần bên trái (thông tin đăng ký)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = DEFAULT_INSETS;
        gbc.anchor = GridBagConstraints.WEST;

        // Tiêu đề "Register"
        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        gbc.gridx = TITLE_X;
        gbc.gridy = TITLE_Y;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(titleLabel, gbc);

        // Nhãn và trường nhập Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = EMAIL_LABEL_X;
        gbc.gridy = EMAIL_LABEL_Y;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(FIELD_WIDTH);
        emailField.setPreferredSize(new Dimension(0, 40));
        gbc.gridx = EMAIL_FIELD_X;
        gbc.gridy = EMAIL_FIELD_Y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        leftPanel.add(emailField, gbc);

        // Nhãn và trường nhập Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = PASSWORD_LABEL_X;
        gbc.gridy = PASSWORD_LABEL_Y;
        leftPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(FIELD_WIDTH);
        passwordField.setPreferredSize(new Dimension(0, 40));
        gbc.gridx = PASSWORD_FIELD_X;
        gbc.gridy = PASSWORD_FIELD_Y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        leftPanel.add(passwordField, gbc);

        // Nhãn và trường nhập Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = CONFIRM_PASSWORD_LABEL_X;
        gbc.gridy = CONFIRM_PASSWORD_LABEL_Y;
        leftPanel.add(confirmPasswordLabel, gbc);

        JPasswordField confirmPasswordField = new JPasswordField(FIELD_WIDTH);
        confirmPasswordField.setPreferredSize(new Dimension(0, 40));
        gbc.gridx = CONFIRM_PASSWORD_FIELD_X;
        gbc.gridy = CONFIRM_PASSWORD_FIELD_Y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        leftPanel.add(confirmPasswordField, gbc);

        // Nút Register
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setPreferredSize(new Dimension(0, 40));
        gbc.gridx = REGISTER_BUTTON_X;
        gbc.gridy = REGISTER_BUTTON_Y;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
// TODO
//            if (!email.isEmpty() && !password.isEmpty() && password.equals(confirmPassword)) {
//                UserService userService = new UserServiceImpl();
//                Response response = userService.register(email, password);
//                if (response.isSuccess()) {
//                    JOptionPane.showMessageDialog(this, "Đăng ký thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//                    mainApp.showPanel(new LoginFrame(mainApp));
//                } else {
//                    JOptionPane.showMessageDialog(this, response.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Thông tin không hợp lệ hoặc mật khẩu không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
        });
        leftPanel.add(registerButton, gbc);

        // Dòng chữ "Already have an account? Login"
        JLabel loginLabel = new JLabel("<html>Already have an account? <u>Login</u></html>");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loginLabel.setForeground(Color.BLACK);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainApp.showPanel(new LoginFrame(mainApp));
            }
        });
        gbc.gridx = LOGIN_LABEL_X;
        gbc.gridy = LOGIN_LABEL_Y;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(loginLabel, gbc);

        // Phần bên phải (hình ảnh)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        JLabel imageLabel = new JLabel();
        java.net.URL imageUrl = getClass().getResource("/images/login_and_register_image.jpg");
        if (imageUrl != null) {
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            Image scaledImage = imageIcon.getImage().getScaledInstance(800, 1000, Image.SCALE_AREA_AVERAGING);
            imageLabel.setIcon(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            imageLabel.setText("Image Not Found");
            imageLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        rightPanel.add(imageLabel, BorderLayout.CENTER);


        // Thêm leftPanel và rightPanel vào RegisterFrame
        add(leftPanel);
        add(rightPanel);
    }
}