package org.smart_job.service.impl;

//import org.smart_job.common.Response;
//import org.smart_job.dao.UserDAO;
//import org.smart_job.dao.impl.UserDAOImpl;
//import org.smart_job.entity.User;
//import org.smart_job.service.UserService;

import jakarta.persistence.EntityManager;
import org.smart_job.common.Response;
import org.smart_job.entity.UserEntity;
import org.smart_job.service.UserService;
import org.smart_job.util.JPAUtil;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public Response<UserEntity> addUser(UserEntity user) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return Response.ok(user, "User added successfully");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return Response.fail("Failed to add user: " + e.getMessage());
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
