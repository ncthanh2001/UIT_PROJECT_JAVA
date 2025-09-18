package org.smart_job.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@IdClass(UserSkill.Id.class)
@Entity
@Table(name = "user_skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSkill extends BaseEntity {
    @jakarta.persistence.Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @jakarta.persistence.Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    // IdClass
    public static class Id implements Serializable {
        private Integer userId;
        private Integer skillId;

        public Id() {}

        public Id(Integer userId, Integer skillId) {
            this.userId = userId;
            this.skillId = skillId;
        }

        // equals and hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            if (!Objects.equals(userId, id.userId)) return false;
            return Objects.equals(skillId, id.skillId);
        }

        @Override
        public int hashCode() {
            int result = userId != null ? userId.hashCode() : 0;
            result = 31 * result + (skillId != null ? skillId.hashCode() : 0);
            return result;
        }
    }
}