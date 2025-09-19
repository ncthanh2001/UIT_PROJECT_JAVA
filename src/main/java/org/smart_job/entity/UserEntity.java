package org.smart_job.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.smart_job.enums.UserRole;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "avatar", length = 200)
    private String avatar;

    @Column(name = "country", length = 50)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private UserRole role = UserRole.USER; // ROLE USER is Default

    @Column(name = "dob")
    private LocalDate dob;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Cv> cvs = new HashSet<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<JobApplication> jobApplications = new HashSet<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserSkillEntity> userSkills = new HashSet<>();
}