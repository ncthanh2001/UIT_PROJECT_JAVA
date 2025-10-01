package org.smart_job.view.profile;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class ProfileContentPanel extends JPanel {
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JComboBox<String> countryComboBox;
    private JDateChooser dobChooser;
    private JButton btnSave;

    public ProfileContentPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initUI();
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblFirstName = new JLabel("First Name:");
        JLabel lblLastName = new JLabel("Last Name:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblCountry = new JLabel("Country:");
        JLabel lblDob = new JLabel("Date of Birth:");

        // Consistent font
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        lblFirstName.setFont(labelFont);
        lblLastName.setFont(labelFont);
        lblEmail.setFont(labelFont);
        lblCountry.setFont(labelFont);
        lblDob.setFont(labelFont);

        txtFirstName = new JTextField(20);
        txtLastName = new JTextField(20);
        txtEmail = new JTextField(20);
        txtEmail.setEnabled(false); // Email is not editable

        // Country comboBox (similar to RegisterView)
        String[] countries = Locale.getISOCountries();
        String[] countryNames = new String[countries.length];
        for (int i = 0; i < countries.length; i++) {
            Locale locale = new Locale("", countries[i]);
            countryNames[i] = locale.getDisplayCountry();
        }
        countryComboBox = new JComboBox<>(countryNames);

        dobChooser = new JDateChooser();
        dobChooser.setDateFormatString("yyyy-MM-dd");

        btnSave = new JButton("Save Profile");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(new Color(70, 130, 180));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; formPanel.add(lblFirstName, gbc);
        gbc.gridx = 1; formPanel.add(txtFirstName, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(lblLastName, gbc);
        gbc.gridx = 1; formPanel.add(txtLastName, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(lblEmail, gbc);
        gbc.gridx = 1; formPanel.add(txtEmail, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(lblCountry, gbc);
        gbc.gridx = 1; formPanel.add(countryComboBox, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(lblDob, gbc);
        gbc.gridx = 1; formPanel.add(dobChooser, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(btnSave, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    // Getter cho controller
    public JTextField getTxtFirstName() { return txtFirstName; }
    public JTextField getTxtLastName() { return txtLastName; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JComboBox<String> getCountryComboBox() { return countryComboBox; }
    public JDateChooser getDobChooser() { return dobChooser; }
    public JButton getSaveButton() { return btnSave; }
}