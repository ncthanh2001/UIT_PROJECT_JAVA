package org.smart_job.service;

import org.smart_job.common.Response;
import org.smart_job.entity.UserEntity;

import java.util.List;

public interface UserService {
    Response<UserEntity> addUser(UserEntity userEntity);
    Response<UserEntity> updateUser(UserEntity userEntity);
    Response<Boolean> deleteUser(Integer id);
    Response<UserEntity> getUserById(Integer id);
    Response<List<UserEntity>> getAllUsers();

//    Response<User> register(User user);
//    Response<User>  updateProfile(User user);
//    Response<Boolean>  deleteUser(Integer id);
//    Response<User> getUserById(Integer id);
//    Response<List<User>> getAllUsers();
}
