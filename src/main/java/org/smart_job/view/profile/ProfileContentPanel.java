package org.smart_job.view.profile;

import javax.swing.*;
import java.awt.*;

public class ProfileContentPanel extends JPanel {
    public ProfileContentPanel() {
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        JLabel label = new JLabel("Welcome to Jobs!", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        add(label, BorderLayout.CENTER);
    }
}
