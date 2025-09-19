package org.smart_job.service;

import org.smart_job.dto.Response;
import org.smart_job.dto.auth.LoginUserDto;
import org.smart_job.dto.auth.RegisterUserDto;
import org.smart_job.entity.UserEntity;

import java.util.List;

public interface UserService {
    Response login(LoginUserDto loginUserDto);
    Response<UserEntity> register(RegisterUserDto registerUserDto);
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
