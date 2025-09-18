package org.smart_job.service.impl;

import jakarta.persistence.EntityManager;
import org.smart_job.dto.Response;
import org.smart_job.dto.auth.RegisterUserDto;
import org.smart_job.entity.UserEntity;
import org.smart_job.service.UserService;
import org.smart_job.util.JPAUtil;
import org.smart_job.util.PasswordUtil;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public Response<UserEntity> login(String email, String password) {
        return null;
    }

    @Override
    public Response<UserEntity> register(RegisterUserDto registerUserDto) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            // Validate password match
            if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
                return Response.fail("Passwords do not match");
            }

            // Kiểm tra email trùng
            Long count = em.createQuery(
                            "SELECT COUNT(u) FROM UserEntity u WHERE u.email = :email",
                            Long.class)
                    .setParameter("email", registerUserDto.getEmail())
                    .getSingleResult();
            if (count > 0) {
                return Response.fail("Email already exists");
            }

            UserEntity user = new UserEntity();
            user.setFirstName(registerUserDto.getFirstName());
            user.setLastName(registerUserDto.getLastName());
            user.setEmail(registerUserDto.getEmail());
            user.setCountry(registerUserDto.getCountry());
            user.setDob(registerUserDto.getDob());

            // Hash password before save
            user.setPassword(PasswordUtil.encode(registerUserDto.getPassword()));

            em.persist(user);
            em.getTransaction().commit();

            return Response.ok(user, "User registered successfully");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return Response.fail("Failed to register user: " + e.getMessage());
        } finally {
            em.close();
        }
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
