package com.ihorchubatenko.spring.web.app.entity;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private Map<Long, User> userDatabase = new HashMap<>();
    private AtomicLong userIdGenerator = new AtomicLong(1);

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
