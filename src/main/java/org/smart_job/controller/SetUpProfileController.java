package org.smart_job.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.view.BaseLayoutView;
import org.smart_job.view.dashboard.DashboardContentPanel;
import org.smart_job.view.SetUpProfileView;

public class SetUpProfileController {
    private final SetUpProfileView view;
    private static final Logger logger = LogManager.getLogger(SetUpProfileController.class);

    public SetUpProfileController(SetUpProfileView view) {
        this.view = view;

        this.view.setSubmitAction(e -> handleSubmit());
    }

    private void handleSubmit() {
        System.out.println("Submitting setup profile...");

        BaseLayoutView layoutView = new BaseLayoutView();
        MainController mainController = MainController.init(layoutView);
        mainController.start();

        this.view.dispose();
    }
}
