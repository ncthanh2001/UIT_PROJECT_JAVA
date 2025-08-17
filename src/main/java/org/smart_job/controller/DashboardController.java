package org.smart_job.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.view.dashboard.DashboardContentPanel;

public class DashboardController {
    private final DashboardContentPanel view;
    private static final Logger logger = LogManager.getLogger(DashboardController.class);

    public DashboardController(DashboardContentPanel view) {
        this.view = view;
        init();
    }

    private void init() {
        // Log current user information
        logger.debug("Current user:");
    }
}
