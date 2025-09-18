package org.smart_job.controller;

import org.smart_job.dto.Response;
import org.smart_job.entity.UserEntity;
import org.smart_job.service.UserService;

public class UserController {
    private final UserService userService;

    // Inject qua constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void register(String email, String name, String password) {
        UserEntity u = new UserEntity();
        u.setEmail(email);
        u.setFirstName(name);
        u.setLastName(name);
        u.setPassword(password);

//        Response<UserEntity> response = userService.register(u);
//        if (response.isSuccess()) {
//            System.out.println("User registered: " + response.getData().getEmail());
//        } else {
//            System.err.println("Error: " + response.getMessage());
//        }
    }
}
