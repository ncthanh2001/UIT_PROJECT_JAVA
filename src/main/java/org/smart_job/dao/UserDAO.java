package org.smart_job.dao;

import org.smart_job.entity.User;

import java.util.List;

public interface UserDAO extends GenericDAO<User,Integer> {
    User findByEmail(String email) throws Exception;
    List<User> findByCountry(String country) throws Exception;
    boolean existsByEmail(String email) throws Exception;
    List<User> searchByName(String name) throws Exception;
}

