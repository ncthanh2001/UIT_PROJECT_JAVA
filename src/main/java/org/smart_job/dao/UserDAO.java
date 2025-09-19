package org.smart_job.dao;

import org.smart_job.entity.UserEntity;

import java.util.List;

public interface UserDAO {
    // Register → insert new user
    void save(UserEntity user);

    // Find user by ID
    UserEntity findById(int id);

    // Find user by email (to check login & check email is exist when register)
    UserEntity findByEmail(String email);

    // Get all users (optional)
    List<UserEntity> findAll();

    // Delete user
    void delete(UserEntity user);

    // Update user
    void update(UserEntity user);
}
