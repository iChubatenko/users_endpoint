package com.ihorchubatenko.spring.web.app.dao;

import com.ihorchubatenko.spring.web.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Long> {

    public List<User> findByFirstName(String firstName);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}
