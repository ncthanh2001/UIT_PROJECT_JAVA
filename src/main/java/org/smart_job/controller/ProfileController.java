package org.smart_job.controller;

import org.smart_job.entity.User;
import org.smart_job.service.UserService;
import org.smart_job.view.profile.ProfileContentPanel;

import javax.swing.*;
import java.time.ZoneId;
import java.util.Date;

public class ProfileController {
    private final ProfileContentPanel view;
    private final UserService userService;
    private User currentUser;

    public ProfileController(ProfileContentPanel view, UserService userService, User currentUser) {
        this.view = view;
        this.userService = userService;
        this.currentUser = currentUser;
        initController();
    }

    private void initController() {
        // Fill dữ liệu user vào form
        fillForm();

        // Gắn sự kiện nút Save
        view.getSaveButton().addActionListener(e -> saveProfile());
    }

    private void fillForm() {
        view.getTxtFirstName().setText(currentUser.getFirstName());
        view.getTxtLastName().setText(currentUser.getLastName());
        view.getTxtEmail().setText(currentUser.getEmail());

        if (currentUser.getDob() != null) {
            Date dob = Date.from(currentUser.getDob().atStartOfDay(ZoneId.systemDefault()).toInstant());
            view.getDobChooser().setDate(dob);
        }

        if (currentUser.getCountry() != null) {
            view.getCountryComboBox().setSelectedItem(currentUser.getCountry());
        }
    }

    private void saveProfile() {
        try {
            // Lấy dữ liệu từ form
            currentUser.setFirstName(view.getTxtFirstName().getText().trim());
            currentUser.setLastName(view.getTxtLastName().getText().trim());
            currentUser.setEmail(view.getTxtEmail().getText().trim());

            Date dobDate = view.getDobChooser().getDate();
            if (dobDate != null) {
                currentUser.setDob(dobDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }

            String country = (String) view.getCountryComboBox().getSelectedItem();
            currentUser.setCountry(country);

            // Gọi UserService update
            var response = userService.updateProfile(currentUser);

            if (response.isSuccess()) {
                JOptionPane.showMessageDialog(view, "Profile updated successfully!");
            } else {
                JOptionPane.showMessageDialog(view, "Update failed: " + response.getMessage());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error updating profile: " + ex.getMessage());
        }
    }
}
