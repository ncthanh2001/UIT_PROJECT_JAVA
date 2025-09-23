package org.smart_job.service;

import org.smart_job.entity.JobApplication;

public interface JobApplicationService {
    JobApplication insert(JobApplication entity) throws Exception;
}
