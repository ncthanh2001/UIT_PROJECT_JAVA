package org.smart_job.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.view.jobs.JobTrackerContentPanel;

public class JobsTrackerController {
    private JobTrackerContentPanel jobTrackerContentPanel;
    private static final Logger logger = LogManager.getLogger(JobsTrackerController.class);

    public JobsTrackerController(JobTrackerContentPanel view) {
        jobTrackerContentPanel = view;
        init();
    }

    private void init() {
        // Log current user information
        logger.info("Current user:");
    }
}
