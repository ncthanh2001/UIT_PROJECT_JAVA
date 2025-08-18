package org.smart_job.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSkill extends BaseEntity {
    private Integer userId;
    private Integer skillId;

    // Relationships
    private User user;
    private Skill skill;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserSkill userSkill = (UserSkill) obj;
        return userId != null && userId.equals(userSkill.userId) &&
                skillId != null && skillId.equals(userSkill.skillId);
    }

    @Override
    public int hashCode() {
        return (userId != null ? userId.hashCode() : 0) +
                (skillId != null ? skillId.hashCode() : 0);
    }
}