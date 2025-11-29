package org.smart_job.service;

import org.smart_job.common.Response;
import org.smart_job.entity.User;

import java.util.List;

public interface UserService {
    Response<User> login(User user);
    Response<User> register(User user);
    Response<User>  updateProfile(User user);
    Response<Boolean>  deleteUser(Integer id);
    Response<User> getUserById(Integer id);
    Response<List<User>> getAllUsers();
}
