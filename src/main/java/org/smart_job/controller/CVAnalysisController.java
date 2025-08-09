package org.smart_job.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.view.CVAnalysis.CVAnalysisContentPanel;

public class CVAnalysisController {
    private final CVAnalysisContentPanel view;
    private static final Logger logger = LogManager.getLogger(CVAnalysisController.class);

    CVAnalysisController(CVAnalysisContentPanel view) {
        this.view = view;
        init();
    }

    private void init() {
        System.out.println("init view");
    }
}
