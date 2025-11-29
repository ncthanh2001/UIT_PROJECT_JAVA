package org.smart_job.view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public class SetUpProfileView extends JFrame {

    private JTextField txtFirstname;
    private JTextField txtLastname;
    private JTextField txtEmail;
    private JComboBox<String> cboCountry;
    private JComboBox<String> cboWorkExperience;
    private JButton btnSubmit;

    public SetUpProfileView() {
        setTitle("Set Up Profile");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Title
        JLabel lblTitle = new JLabel("Set Up Profile", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        add(lblTitle, BorderLayout.NORTH);

        // Subtitle
        JLabel lblSubtitle = new JLabel("Complete your information for the best experience", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lblSubtitle, BorderLayout.CENTER);

        // Form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtFirstname = new JTextField();
        txtLastname = new JTextField();
        txtEmail = new JTextField();
        cboCountry = new JComboBox<>(new String[] {"Select Your Country", "Vietnam", "USA", "Germany"});
        cboWorkExperience = new JComboBox<>(new String[] {
                "Select Your Level of Work Experience",
                "0 - 1 years", "1 - 3 years", "3 - 5 years", "5+ years"
        });

        // Row 1: Firstname / Lastname
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Firstname"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtFirstname, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Lastname"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtLastname, gbc);

        // Row 2: Email / Country
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Email"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Country"), gbc);
        gbc.gridx = 3;
        formPanel.add(cboCountry, gbc);

        // Row 3: Work Experience
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Work Experience"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        formPanel.add(cboWorkExperience, gbc);

        // Row 4: Submit button
        gbc.gridx = 3; gbc.gridy = 3;
        gbc.gridwidth = 1;
        btnSubmit = new JButton("Submit");
        formPanel.add(btnSubmit, gbc);

        add(formPanel, BorderLayout.SOUTH);
    }

    public void setSubmitAction(ActionListener listener) {
        btnSubmit.addActionListener(listener);
    }
}
