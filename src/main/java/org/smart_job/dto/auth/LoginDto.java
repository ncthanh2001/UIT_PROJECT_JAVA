package org.smart_job.dto.auth;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
