package org.smart_job.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import org.smart_job.dao.UserDAO;
import org.smart_job.entity.UserEntity;
import org.smart_job.util.JPAUtil;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();


    @Override
    public void save(UserEntity user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public UserEntity findById(int id) {
        EntityManager em = emf.createEntityManager();
        UserEntity user = em.find(UserEntity.class, id);
        em.close();
        return user;
    }

    @Override
    public UserEntity findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<UserEntity> findAll() {
        EntityManager em = emf.createEntityManager();
        List<UserEntity> list = em.createQuery("SELECT u FROM UserEntity u", UserEntity.class)
                .getResultList();
        em.close();
        return list;
    }

    @Override
    public void delete(UserEntity user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        UserEntity managed = em.merge(user); // attach entity
        em.remove(managed);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(UserEntity user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }
}
