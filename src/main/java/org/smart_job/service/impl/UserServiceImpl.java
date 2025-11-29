package org.smart_job.service.impl;

import org.smart_job.common.Response;
import org.smart_job.dao.UserDAO;
import org.smart_job.dao.impl.UserDAOImpl;
import org.smart_job.entity.User;
import org.smart_job.service.UserService;
import org.smart_job.util.PasswordUtil;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDAO userDao = new UserDAOImpl();

    @Override
    public Response<User> login(User user) {
        try {
            User userInDb = userDao.findByEmail(user.getEmail());
            if (userInDb == null) {
                return Response.fail("User not found");
            }

            // So sánh password người dùng nhập với hash trong DB
            if (!PasswordUtil.matches(user.getPassword(), userInDb.getPassword())) {
                return Response.fail("Invalid password");
            }

            return Response.ok(userInDb);
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }

    @Override
    public Response<User> register(User user)   {
       try{
           if (userDao.findByEmail(user.getEmail()) != null) {
               return Response.fail("Email already exists");
           }

           // Hash password trước khi lưu vào DB
           String hashedPassword = PasswordUtil.encode(user.getPassword());
           user.setPassword(hashedPassword);

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
