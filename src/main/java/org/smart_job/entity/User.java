package org.smart_job.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
/*
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(255),
    password VARCHAR(255),
    image VARCHAR(255),
    country VARCHAR(100),
    dob DATE,
    created_at DATETIME,
    updated_at DATETIME
);
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{
    private Integer id;
    private String userName;
    private String lastName;
    private String email;
    private String password;
    private String image;
    private String country;
    private Date dob;
}
