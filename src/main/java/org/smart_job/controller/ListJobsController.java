package org.smart_job.controller;

import org.smart_job.view.jobs.ListJobsContentPannel;

public class ListJobsController {
    private ListJobsContentPannel listJobsContentPannel;

    public ListJobsController(ListJobsContentPannel view) {
        listJobsContentPannel = view;
        init();
    }

    private void init() {
        System.out.println("init view list jobs");
    }
}
