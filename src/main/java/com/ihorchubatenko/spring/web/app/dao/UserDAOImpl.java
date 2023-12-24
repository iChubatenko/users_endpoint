package com.ihorchubatenko.spring.web.app.dao;

import com.ihorchubatenko.spring.web.app.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        User newUser = entityManager.merge(user);
        user.setId(newUser.getId());
    }

    @Override
    public User getUserById(long userId) {
        User user = entityManager.find(User.class, userId);
        return user;
    }

    @Override
    public List<User> getAllUsers(){

        Query query = entityManager.createQuery("from User");
        List<User> allUsers = query.getResultList();

        return allUsers;
    }

    @Override
    public void deleteUser(long userId) {

        Query query = entityManager.createQuery("delete from User where id =: userId");
        query.setParameter("userId", userId);
        query.executeUpdate();
    }
}
