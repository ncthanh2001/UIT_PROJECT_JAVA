package org.smart_job.dto.auth;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String country;
    private LocalDate dob;
}
