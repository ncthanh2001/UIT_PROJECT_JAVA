package org.smart_job.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String avatar;
    private String country;
    private LocalDate dob;

    // Relationships
//    private List<CV> cvs;
//    private List<Skill> skills;
//    private List<JobApplication> jobApplications;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
