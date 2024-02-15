package com.ihorchubatenko.spring.web.app.dao;

import com.ihorchubatenko.spring.web.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserDAO extends JpaRepository <User, Long> {

    public List<User> findByFirstName(String firstName);

    public User findByUsername(String username);
}
