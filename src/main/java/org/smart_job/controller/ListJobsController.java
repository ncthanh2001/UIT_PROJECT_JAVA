package org.smart_job.controller;

import org.smart_job.entity.*;
import org.smart_job.service.JobApplicationService;
import org.smart_job.service.JobService;
import org.smart_job.service.SkillService;
import org.smart_job.service.impl.JobApplicationServiceImpl;
import org.smart_job.service.impl.JobServiceImpl;
import org.smart_job.service.impl.SkillServiceImpl;
import org.smart_job.session.UserSession;
import org.smart_job.view.jobs.ListJobsContentPannel;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListJobsController {
    private final ListJobsContentPannel view;
    private final JobService jobService;
    private final SkillService skillService;
    private final JobApplicationService jobApplicationService;

    public ListJobsController(ListJobsContentPannel view) {
        this.view = view;
        this.jobService = new JobServiceImpl();
        this.skillService = new SkillServiceImpl();
        this.jobApplicationService = new JobApplicationServiceImpl();
        init();
    }

    private void init() {
        try {
            // Load countries & cities
            loadFilters();

            // Load initial jobs page 1
            loadJobs(1);

            // Search button
            view.getSearchButton().addActionListener(e -> loadJobs(1));

            // Pagination buttons
            view.getFirstPageButton().addActionListener(e -> loadJobs(1));
            view.getPrevPageButton().addActionListener(e -> {
                if (view.currentPage > 1) loadJobs(view.currentPage - 1);
            });
            view.getNextPageButton().addActionListener(e -> {
                if (view.currentPage < view.totalPages) loadJobs(view.currentPage + 1);
            });
            view.getLastPageButton().addActionListener(e -> loadJobs(view.totalPages));

            // Country selection updates city list
            view.getCountryComboBox().addActionListener(e -> {
                try {
                    updateCityComboBox();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });


        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view,
                    "Error initializing jobs: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFilters() throws Exception {
        List<String> locations = jobService.getUniqueLocations(); // format: "City, Country"
        Set<String> countries = locations.stream()
                .map(loc -> loc.contains(",") ? loc.split(",")[1].trim() : loc.trim())
                .collect(Collectors.toSet());

        view.getCountryComboBox().removeAllItems();
        view.getCountryComboBox().addItem("All Countries");
        countries.forEach(view.getCountryComboBox()::addItem);

        updateCityComboBox(); // load cities trong current country
    }

    private void updateCityComboBox() throws Exception {
        String selectedCountry = (String) view.getCountryComboBox().getSelectedItem();
        view.getCityComboBox().removeAllItems();
        view.getCityComboBox().addItem("All Cities");

        List<String> locations = jobService.getUniqueLocations();
        Set<String> cities = locations.stream()
                .filter(loc -> "All Countries".equals(selectedCountry) || loc.endsWith(selectedCountry))
                .map(loc -> loc.contains(",") ? loc.split(",")[0].trim() : loc.trim())
                .collect(Collectors.toSet());

        cities.forEach(view.getCityComboBox()::addItem);

        view.getCityComboBox().setEnabled(!"All Countries".equals(selectedCountry));
    }

    private void loadJobs(int page) {
        view.clearJobs();
        try {
            String keyword = view.getSearchText().trim();
            String country = (String) view.getCountryComboBox().getSelectedItem();
            String city = (String) view.getCityComboBox().getSelectedItem();

            // Chuyển "All Countries"/"All Cities" thành null để query DB
            if ("All Countries".equals(country)) country = null;
            if ("All Cities".equals(city)) city = null;
            if (keyword.isEmpty()) keyword = null;

            // Count tổng job để tính total page
            int total = jobService.countJobs(keyword, country, city);
            view.totalPages = Math.max(1, (int) Math.ceil((double) total / view.pageSize));
            view.currentPage = Math.min(Math.max(page, 1), view.totalPages);

            // Lấy jobs trong page hiện tại
            List<Job> jobsPage = jobService.findJobs(keyword, country, city, view.currentPage, view.pageSize);

            for (Job job : jobsPage) {
                List<String> skills = skillService.getSkillsByJobId(job.getId())
                        .stream()
                        .map(Skill::getName)
                        .collect(Collectors.toList());
                String matchPercent = (50 + (int) (Math.random() * 50)) + "%";
                view.addJobCard(job, matchPercent, skills);

                attachApplyAction(job); // Insert to JobApplication
            }

            view.getPageLabel().setText("Page " + view.currentPage + " / " + view.totalPages);

        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading jobs: " + e.getMessage());
        }
    }


    private void attachApplyAction(Job job) {
        view.getApplyBtn().addActionListener(e -> {
            int result = showConfirmDialog("Xác nhận ứng tuyển", "Bạn có chắc muốn gửi CV tới " + job.getCompanyName() + "?");

            if (result == JOptionPane.YES_OPTION) {
                try {
                    User currentUser = UserSession.getInstance().getCurrentUser();

                    JobApplication newJobApplication = new JobApplication();
                    newJobApplication.setUserId(currentUser.getId());
                    newJobApplication.setJobId(job.getId());
                    newJobApplication.setCustomTitle(job.getTitle());
                    newJobApplication.setCustomCompany(job.getCompanyName());
                    newJobApplication.setCustomDescription(job.getDescription());
                    newJobApplication.setCustomUrl(job.getUrl());
                    newJobApplication.setApplicationDate(LocalDateTime.now());
                    newJobApplication.setStatus(JobStatus.APPLIED);
                    newJobApplication.setNotes(""); // TODO: option notes

                    JobApplication res = jobApplicationService.insert(newJobApplication);
                    if (res != null) {
                        showSuccess("CV của bạn đã được gửi tới " + job.getCompanyName());
                    } else {
                        showWarning("Bạn đã ứng tuyển vị trí này");
                    }
                } catch (Exception ex) {
                    showWarning(ex.getMessage());
                    throw new RuntimeException(ex);
                }


            }
        });
    }

    // --- UI Helpers ---
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(view, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.WARNING_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private int showConfirmDialog(String title, String message) {
        return JOptionPane.showConfirmDialog(view, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
}
