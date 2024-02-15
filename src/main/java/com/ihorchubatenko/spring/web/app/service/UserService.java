package com.ihorchubatenko.spring.web.app.service;

import com.ihorchubatenko.spring.web.app.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    public User saveUser(User user);
    public User getUserById(long userId);
    public List<User> getAllUsers();
    public void deleteUser(long userId);
    public List<User> findByFirstName(String firstName);
//    public void addUserDetails(User user);
}
