package com.ihorchubatenko.spring.web.app.dao;

import com.ihorchubatenko.spring.web.app.entity.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RoleDAO extends CrudRepository<Role, Integer> {

    Optional<Role> findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_roles WHERE user_id = :userId", nativeQuery = true)
    void deleteRolesByUserId(@Param("userId") Long userId);
}
