package com.ihorchubatenko.spring.web.app.service;

import com.ihorchubatenko.spring.web.app.dao.UserDAO;
import com.ihorchubatenko.spring.web.app.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDAO userDAO;

    @Override
    public User saveUser(User user) {
        userDAO.save(user);
        logger.debug("Saved to the database: {}", user);
        return user;
    }

    @Override
    public User getUserById(long userId) {
        User user = null;
        Optional<User> optional = userDAO.findById(userId);
        if (optional.isPresent()){
            user = optional.get();
        }
        logger.debug("Taken from the database the user with id={}", userId);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        logger.debug("Taken from the database list of all users");
        return userDAO.findAll();
    }

    @Override
    public void deleteUser(long userId) {
        userDAO.deleteById(userId);
        logger.debug("Removed from the database the user with id={}", userId);
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        List<User> usersByName = userDAO.findByFirstName(firstName);
        logger.debug("Taken from the database list of all users with firstName: {}", firstName);
        return usersByName;
    }

//    @Override
//    public void addUserDetails(User user) {
//        if(userDAO.findByUsername(user.getUsername()) != null){
//            throw new RuntimeException("Username already exists");
//        }
//
//        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
//        user.setPassword(encodedPassword);
//        userDAO.save(user);
//    }


}
