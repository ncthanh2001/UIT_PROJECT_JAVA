package org.smart_job.controller;

import org.smart_job.entity.Job;
import org.smart_job.entity.Skill;
import org.smart_job.service.JobService;
import org.smart_job.service.SkillService;
import org.smart_job.service.impl.JobServiceImpl;
import org.smart_job.service.impl.SkillServiceImpl;
import org.smart_job.view.jobs.ListJobsContentPannel;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class ListJobsController {
    private final ListJobsContentPannel view;
    private final JobService jobService;
    private final SkillService skillService;

    public ListJobsController(ListJobsContentPannel view) {
        this.view = view;
        this.jobService = new JobServiceImpl();
        this.skillService = new SkillServiceImpl();
        init();
    }

    private void init() {
        // Load initial jobs
        loadJobs("");

        // Search event
        view.getSearchButton().addActionListener(e -> {
            String keyword = view.getSearchText().trim();
            loadJobs(keyword);
        });
    }

    private void loadJobs(String keyword) {
        view.clearJobs();

        try {
            List<Job> jobs;
            if (keyword == null || keyword.isEmpty()) {
                jobs = jobService.getAllJobs();
            } else {
                jobs = jobService.searchJobs(keyword);
            }

            for (Job job : jobs) {
                List<String> skills = skillService.getSkillsByJobId(job.getId())
                        .stream()
                        .map(Skill::getName)
                        .collect(Collectors.toList());

                // TODO: tạm để random
                String matchPercent = (50 + (int)(Math.random() * 50)) + "%";

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
