package org.smart_job;


import org.smart_job.view.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    private JPanel currentPanel;

    public MainApp() {
        setTitle("Smart Job Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Hiển thị trang đăng nhập đầu tiên
        showPanel(new LoginFrame(this));
    }

    public void showPanel(JPanel newPanel) {
        // Xóa panel hiện tại nếu có
        if (currentPanel != null) {
            remove(currentPanel);
        }
        // Thêm panel mới
        currentPanel = newPanel;
        add(currentPanel, BorderLayout.CENTER);
        // Cập nhật giao diện
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        // Thiết lập FlatLaf trước khi tạo giao diện
//        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp();
            app.setVisible(true);
        });
    }
}