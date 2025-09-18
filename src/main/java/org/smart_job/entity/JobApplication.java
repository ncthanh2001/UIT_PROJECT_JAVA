package org.smart_job.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication extends BaseEntity {
    private Integer id;
    private Integer userId;
    private Integer jobId;
    private String customTitle;
    private String customCompany;
    private String customDescription;
    private String customUrl;
    private LocalDateTime applicationDate;
    private JobStatus status;
    private String notes;

    // Relationships
    private UserEntity userEntity;
    private Job job;

    public String getCompanyName() {
        if (job != null) {
            return job.getCompanyName();
        }
        return customCompany;
    }

    public String getJobTitle() {
        if (job != null) {
            return job.getTitle();
        }
        return customTitle;
    }

    public String getJobDescription() {
        if (job != null) {
            return job.getDescription();
        }
        return customDescription;
    }

    public String getJobUrl() {
        if (job != null) {
            return job.getUrl();
        }
        return customUrl;
    }

    public boolean isCustomApplication() {
        return jobId == null;
    }
}