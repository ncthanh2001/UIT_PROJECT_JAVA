package org.smart_job.dto.auth;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String password;
}
