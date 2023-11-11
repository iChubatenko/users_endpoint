package com.ihorchubatenko.spring.web.app.dao;

import java.util.List;

public interface UserRepository {

    User saveUser(User user);
    User getUserById(long userId);
    List<User> getAllUsers();
    User deleteUser(long userId);

}
