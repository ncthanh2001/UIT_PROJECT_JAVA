package org.smart_job.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job extends BaseEntity {
    private Integer id;
    private String title;
    private String companyName;
    private String country;
    private String city;
    private String url;
    private String description;
    private LocalDateTime expirationDate;

    // Relationships
    private List<Skill> skills;
    private List<JobApplication> jobApplications;

    public boolean isExpired() {
        return expirationDate != null && expirationDate.isBefore(LocalDateTime.now());
    }

    public String getLocation() {
        if (city != null && country != null) {
            return city + ", " + country;
        } else if (country != null) {
            return country;
        } else if (city != null) {
            return city;
        }
        return "Remote";
    }
}