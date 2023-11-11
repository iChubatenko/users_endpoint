package com.ihorchubatenko.spring.web.app.dao;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository {

    Map<Long, User> userDatabase;
    AtomicLong userIdGenerator;

    public UserRepositoryImpl(Map<Long, User> userDatabase, AtomicLong userIdGenerator) {
        this.userDatabase = userDatabase;
        this.userIdGenerator = userIdGenerator;
    }

    @Override
    public User saveUser(User user) {
        if (userDatabase.containsKey(user.getId())) {
            long id = user.getId();
            user.setId(id);
            userDatabase.replace(user.getId(), user);
            return user;
        } else {
            long id = userIdGenerator.getAndIncrement();
            user.setId(id);
            userDatabase.put(id, user);
            return user;
        }
    }

    @Override
    public User getUserById(long userId) {
        return userDatabase.get(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userDatabase.values());
    }

    @Override
    public User deleteUser(long userId) {
        return userDatabase.remove(userId);
    }
}
