package com.ihorchubatenko.spring.web.app.dao;

import com.ihorchubatenko.spring.web.app.entity.User;
import java.util.List;

public interface UserDAO {

    public void saveUser(User user);
    public User getUserById(long userId);
    public List<User> getAllUsers();
    public void deleteUser(long userId);
}
