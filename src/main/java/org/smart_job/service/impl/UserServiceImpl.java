package org.smart_job.service.impl;

import org.smart_job.common.Response;
import org.smart_job.dao.UserDao;
import org.smart_job.dao.impl.UserDaoImpl;
import org.smart_job.entity.User;
import org.smart_job.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();
    @Override
    public Response<User> register(User user)   {
       try{
            User userNew = userDao.insert(user);
            return Response.ok(userNew);
       }catch (Exception e)
       {
            return Response.fail(e.getMessage());
       }
    }

    @Override
    public  Response<User> updateProfile(User user) {
        try{
            User userNew = userDao.update(user);
            return Response.ok(userNew);
        }catch (Exception e)
        {
            return Response.fail(e.getMessage());
        }
    }

    @Override
    public Response<Boolean> deleteUser(Integer id) {
        try {
            int result  = userDao.delete(id);
            if(result > 0)
                return Response.ok(null,"Xoá user thành công");
            else
                return Response.ok(null,"Không tìm thấy user để xoá");
        }catch (Exception e )
        {
            return Response.fail(e.getMessage());
        }
    }

    @Override
    public Response<User> getUserById(Integer id) {
        try {
            return Response.ok(userDao.findById(id));
        }catch (Exception e)
        {
            return Response.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<User>> getAllUsers() {
        try {
            return Response.ok(userDao.findAll());
        }catch (Exception e)
        {
            return Response.fail(e.getMessage());
        }
    }
}
