package org.smart_job.controller;

import org.smart_job.entity.Job;
import org.smart_job.entity.Skill;
import org.smart_job.service.JobService;
import org.smart_job.service.SkillService;
import org.smart_job.service.impl.JobServiceImpl;
import org.smart_job.service.impl.SkillServiceImpl;
import org.smart_job.view.jobs.ListJobsContentPannel;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class ListJobsController {
    private final ListJobsContentPannel view;
    private final JobService jobService;
    private final SkillService skillService;
    private Map<String, Set<String>> countryCityMap = new HashMap<>();

    public ListJobsController(ListJobsContentPannel view) {
        this.view = view;
        this.jobService = new JobServiceImpl();
        this.skillService = new SkillServiceImpl();
        init();
    }

    private void init() {
        try {
            // Load filters
            loadFilters();

            // Disable city lúc đầu
            view.getCityComboBox().setEnabled(false);

            // Event khi chọn country
            view.getCountryComboBox().addActionListener(e -> {
                String selectedCountry = (String) view.getCountryComboBox().getSelectedItem();
                updateCitiesForCountry(selectedCountry);
            });

            // Load initial jobs
            loadJobs("", "All Countries", "All Cities");

            // Search event
            view.getSearchButton().addActionListener(e -> {
                String keyword = view.getSearchText().trim();
                String country = (String) view.getCountryComboBox().getSelectedItem();
                String city = (String) view.getCityComboBox().getSelectedItem();
                loadJobs(keyword, country, city);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view,
                    "Error initializing jobs: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFilters() throws Exception {
        // Lấy location từ DB
        List<String> locations = jobService.getUniqueLocations(); // ví dụ "Hanoi, Vietnam"

        countryCityMap.clear();
        for (String loc : locations) {
            if (!loc.contains(",")) continue;
            String[] parts = loc.split(",");
            String city = parts[0].trim();
            String country = parts[1].trim();

            countryCityMap.computeIfAbsent(country, k -> new HashSet<>()).add(city);
        }

        // Load country
        JComboBox<String> countryBox = view.getCountryComboBox();
        countryBox.removeAllItems();
        countryBox.addItem("All Countries");
        for (String country : countryCityMap.keySet()) {
            countryBox.addItem(country);
        }

        // Reset city
        updateCitiesForCountry("All Countries");
    }

    private void updateCitiesForCountry(String country) {
        JComboBox<String> cityBox = view.getCityComboBox();
        cityBox.removeAllItems();

        if (country == null || "All Countries".equals(country)) {
            cityBox.addItem("All Cities");
            cityBox.setEnabled(false);
        } else {
            cityBox.addItem("All Cities");
            for (String city : countryCityMap.getOrDefault(country, Collections.emptySet())) {
                cityBox.addItem(city);
            }
            cityBox.setEnabled(true);
        }
    }

    private void loadJobs(String jobTitle, String country, String city) {
        view.clearJobs();

        try {
            // Lấy tất cả jobs từ DB một lần
            List<Job> jobs = jobService.getAllJobs();

            // Lọc bằng Stream
            jobs = jobs.stream()
                    .filter(job -> {
                        if (jobTitle != null && !jobTitle.isEmpty()) {
                            return job.getTitle().toLowerCase().contains(jobTitle.toLowerCase());
                        }
                        return true;
                    })
                    .filter(job -> {
                        if (country != null && !"All Countries".equals(country)) {
                            return country.equalsIgnoreCase(job.getCountry());
                        }
                        return true;
                    })
                    .filter(job -> {
                        if (city != null && !"All Cities".equals(city)) {
                            return city.equalsIgnoreCase(job.getCity());
                        }
                        return true;
                    })
                    .collect(Collectors.toList());

            // Render jobs
            for (Job job : jobs) {
                List<String> skills = skillService.getSkillsByJobId(job.getId())
                        .stream()
                        .map(Skill::getName)
                        .collect(Collectors.toList());

                String matchPercent = (50 + (int) (Math.random() * 50)) + "%";

                view.addJobCard(job, matchPercent, skills);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    view,
                    "Error loading jobs: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

}
