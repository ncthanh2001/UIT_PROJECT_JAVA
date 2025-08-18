package org.smart_job.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobSkill extends BaseEntity {
    private Integer jobId;
    private Integer skillId;

    // Relationships
    private Job job;
    private Skill skill;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JobSkill jobSkill = (JobSkill) obj;
        return jobId != null && jobId.equals(jobSkill.jobId) &&
                skillId != null && skillId.equals(jobSkill.skillId);
    }

    @Override
    public int hashCode() {
        return (jobId != null ? jobId.hashCode() : 0) +
                (skillId != null ? skillId.hashCode() : 0);
    }
}