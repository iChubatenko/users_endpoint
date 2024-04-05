package com.ihorchubatenko.spring.web.app.controller;

import com.ihorchubatenko.spring.web.app.dao.RoleDAO;
import com.ihorchubatenko.spring.web.app.dao.UserDAO;
import com.ihorchubatenko.spring.web.app.entity.User;
import com.ihorchubatenko.spring.web.app.service.UserService;
import com.ihorchubatenko.spring.web.app.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class UsersControllerTest {

    @Test
    public void testGetUserByID() {
        User user = new User(1L, "Ihor", "Chubatenko");

        UserService mockRepo = Mockito.mock(UserService.class);
        Mockito.when(mockRepo.getUserById(1)).thenReturn(user);

        UsersController testedInstance = new UsersController(mockRepo);

        ResponseEntity<User> userByID = testedInstance.getUserByID(1L);

        assertEquals(user, userByID.getBody());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Ihor", "Chubatenko"));
        users.add(new User(2, "Serhii", "Zapalskiy"));

        UserService mockRepo = Mockito.mock(UserService.class);
        Mockito.when(mockRepo.getAllUsers()).thenReturn(users);

        UsersController testedInstance = new UsersController(mockRepo);

        ResponseEntity<List<User>> allUsers = testedInstance.getAllUsers();

        assertEquals(users, allUsers.getBody());

    }

    @Test
    public void testCreateUser() {
        User testUser = new User(1, "Ihor", "Chubatenko");

        UserDAO mockUserDAO = Mockito.mock(UserDAO.class);
        Mockito.when(mockUserDAO.existsByUsername(Mockito.anyString())).thenReturn(false);

        RoleDAO mockRoleDAO = Mockito.mock(RoleDAO.class);
        Mockito.when(mockRoleDAO.findByName(Mockito.anyString())).thenReturn(Optional.empty());

        BCryptPasswordEncoder mockPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        Mockito.when(mockPasswordEncoder.encode(Mockito.any(CharSequence.class))).thenReturn("hashedPassword");

        UserService mockRepo = new UserServiceImpl(mockUserDAO, mockPasswordEncoder);

        UsersController testedInstance = new UsersController(mockRepo);
        testedInstance.setUserDAO(mockUserDAO);
        testedInstance.setRoleDAO(mockRoleDAO);

        ResponseEntity<?> savedUser = testedInstance.createUser(testUser);

        assertEquals(testUser, savedUser.getBody());
    }

    @Test
    public void testUpdateUser() {
        User testUser = new User(1, "Ihor", "Chubatenko");

        UserService mockRepo = Mockito.mock(UserService.class);
        Mockito.when(mockRepo.getUserById(1L)).thenReturn(testUser);
        Mockito.when(mockRepo.saveUser(any(User.class))).thenReturn(testUser);
        Mockito.when(mockRepo.getUserById(2L)).thenReturn(null);

        UsersController testedInstance = new UsersController(mockRepo);

        ResponseEntity<User> savedUser = testedInstance.updateUser(1L, testUser);

        assertEquals(testUser, savedUser.getBody());

    }

    @Test
    public void testDeleteUser() {
        User testUser = new User(1, "Ihor", "Chubatenko");
        UserService mockRepo = Mockito.mock(UserService.class);
        Mockito.when(mockRepo.getUserById(1L)).thenReturn(testUser);
        mockRepo.deleteUser(1L);
        Mockito.when(mockRepo.getUserById(2L)).thenReturn(null);

        Mockito.verify(mockRepo, Mockito.times(1)).deleteUser(1L);
    }
}
