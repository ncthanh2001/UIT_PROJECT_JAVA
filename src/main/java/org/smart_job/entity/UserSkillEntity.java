package org.smart_job.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "user_skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillEntity extends BaseEntity {
    @EmbeddedId
    private Id id;

    @MapsId("userId") // ánh xạ field userId trong Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @MapsId("skillId") // ánh xạ field skillId trong Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private SkillEntity skillEntity;

    // Composite key
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id implements Serializable {
        @Column(name = "user_id")   // mapping column in DB
        private Integer userId;

        @Column(name = "skill_id")   // mapping column in DB
        private Integer skillId;
    }
}