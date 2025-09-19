package org.smart_job.service.impl;

import org.smart_job.dao.UserDAO;
import org.smart_job.dao.impl.UserDAOImpl;
import org.smart_job.dto.Response;
import org.smart_job.dto.auth.LoginUserDto;
import org.smart_job.dto.auth.RegisterUserDto;
import org.smart_job.entity.UserEntity;
import org.smart_job.service.UserService;
import org.smart_job.util.PasswordUtil;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public Response login(LoginUserDto loginUserDto) {
        UserEntity user = userDAO.findByEmail(loginUserDto.getEmail());

        if (user == null) return Response.fail("User not found");
        boolean passwordOk = PasswordUtil.matches(loginUserDto.getPassword(), user.getPassword());

        if (!passwordOk) return Response.fail("Wrong password");

        return Response.ok(user, "Login success");
    }

    @Override
    public Response<UserEntity> register(RegisterUserDto registerUserDto) {

        // Validate password match
        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            return Response.fail("Passwords do not match");
        }

        // Check email is exist
        UserEntity user = userDAO.findByEmail(registerUserDto.getEmail());
        if (user != null) {
            return Response.fail("Email already exists");
        }

        // Build new User Entity
        UserEntity newUser = new UserEntity();
        newUser.setFirstName(registerUserDto.getFirstName());
        newUser.setLastName(registerUserDto.getLastName());
        newUser.setEmail(registerUserDto.getEmail());
        newUser.setCountry(registerUserDto.getCountry());
        newUser.setDob(registerUserDto.getDob());

        // Hash password before save
        newUser.setPassword(PasswordUtil.encode(registerUserDto.getPassword()));

        userDAO.save(newUser);

        return Response.ok(newUser, "User registered successfully");

    }


    @Override
    public Response<UserEntity> updateUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public Response<Boolean> deleteUser(Integer id) {
        return null;
    }

    @Override
    public Response<UserEntity> getUserById(Integer id) {
        return null;
    }

    @Override
    public Response<List<UserEntity>> getAllUsers() {
        return null;
    }

//    private final UserDAO userDao = new UserDAOImpl();
//    @Override
//    public Response<User> register(User user)   {
//       try{
//            User userNew = userDao.insert(user);
//            return Response.ok(userNew);
//       }catch (Exception e)
//       {
//            return Response.fail(e.getMessage());
//       }
//    }
//
//    @Override
//    public  Response<User> updateProfile(User user) {
//        try{
//            User userNew = userDao.update(user);
//            return Response.ok(userNew);
//        }catch (Exception e)
//        {
//            return Response.fail(e.getMessage());
//        }
//    }
//
//    @Override
//    public Response<Boolean> deleteUser(Integer id) {
//        try {
//            int result  = userDao.delete(id);
//            if(result > 0)
//                return Response.ok(null,"Xoá user thành công");
//            else
//                return Response.ok(null,"Không tìm thấy user để xoá");
//        }catch (Exception e )
//        {
//            return Response.fail(e.getMessage());
//        }
//    }
//
//    @Override
//    public Response<User> getUserById(Integer id) {
//        try {
//            return Response.ok(userDao.findById(id));
//        }catch (Exception e)
//        {
//            return Response.fail(e.getMessage());
//        }
//    }
//
//    @Override
//    public Response<List<User>> getAllUsers() {
//        try {
//            return Response.ok(userDao.findAll());
//        }catch (Exception e)
//        {
//            return Response.fail(e.getMessage());
//        }
//    }
}
