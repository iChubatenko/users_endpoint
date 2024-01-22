package com.ihorchubatenko.spring.web.app.service;

import com.ihorchubatenko.spring.web.app.dao.UserDAO;
import com.ihorchubatenko.spring.web.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDAO userDAO;

    @Override
    public User saveUser(User user) {
        userDAO.save(user);
        return user;
    }

    @Override
    public User getUserById(long userId) {
        User user = null;
        Optional<User> optional = userDAO.findById(userId);
        if (optional.isPresent()){
            user = optional.get();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public void deleteUser(long userId) {
        userDAO.deleteById(userId);
    }

    @Override
    public List<User> findByName(String name) {
        List<User> usersByName = userDAO.findByName(name);
        return usersByName;
    }
}
