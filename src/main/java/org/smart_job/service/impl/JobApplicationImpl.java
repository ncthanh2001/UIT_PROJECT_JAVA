package org.smart_job.service.impl;

import org.smart_job.dao.JobApplicationDAO;
import org.smart_job.dao.impl.JobApplicationDAOImpl;
import org.smart_job.entity.JobApplication;
import org.smart_job.service.JobApplicationService;

import java.util.List;

public class JobApplicationImpl implements JobApplicationService {
    JobApplicationDAO  jobApplicationDAO = new JobApplicationDAOImpl();
    @Override
    public JobApplication insert(JobApplication entity) throws Exception {
        // Kiểm tra user đã apply chưa
        boolean check = isUserAppleidJob(entity.getUserId(), entity.getJobId());

        if (check) {
            return null;
        }

        return jobApplicationDAO.insert(entity);
    }

    private boolean isUserAppleidJob(Integer userId, Integer jobId) throws Exception {
        // Kiểm tra user đã apply chưa
        List<JobApplication> existingApplications = jobApplicationDAO.findByUserId(userId);
        
        if (existingApplications == null || existingApplications.isEmpty()) {
            return false;
        }

        boolean alreadyApplied = existingApplications.stream()
                .anyMatch(app -> app.getJobId().equals(jobId));

       return alreadyApplied;
    }

}
