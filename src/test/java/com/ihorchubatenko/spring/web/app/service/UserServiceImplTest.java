package com.ihorchubatenko.spring.web.app.service;

import com.ihorchubatenko.spring.web.app.dao.RoleDAO;
import com.ihorchubatenko.spring.web.app.dao.UserDAO;
import com.ihorchubatenko.spring.web.app.entity.User;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.mockito.Mockito.*;

public class UserServiceImplTest extends TestCase {

    @Test
    public void testSaveUser() {

        User newUser = new User(1, "Ihor", "Chubatenko");
        newUser.setPassword("password");

        UserDAO mockUserDAO = Mockito.mock(UserDAO.class);
        BCryptPasswordEncoder mockPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

        when(mockPasswordEncoder.encode(newUser.getPassword())).thenReturn("password");

        UserServiceImpl userService = new UserServiceImpl(mockUserDAO, mockPasswordEncoder);

        User savedUser = userService.saveUser(newUser);
        verify(mockPasswordEncoder).encode(newUser.getPassword());
        verify(mockUserDAO).save(newUser);
        assertEquals(newUser, savedUser);
    }

    @Test
    public void testGetUserById() {
        long userID = 1L;
        User expectedUser = new User(userID, "Ihor", "Chubatenko");

        UserDAO mockUserDAO = Mockito.mock(UserDAO.class);
        when(mockUserDAO.findById(userID)).thenReturn(Optional.of(expectedUser));

        UserServiceImpl userService = new UserServiceImpl(mockUserDAO, new BCryptPasswordEncoder());

        User retrievedUser = userService.getUserById(userID);
        verify(mockUserDAO).findById(userID);
        assertEquals(expectedUser, retrievedUser);
    }

    @Test
    public void testGetAllUsers() {
        int expectedUserCount = 2;

        User user1 = new User(1, "Ihor", "Chubatenko");
        User user2 = new User(2, "Serhii", "Zapalskii");
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user1);
        expectedUsers.add(user2);

        UserDAO mockUserDAO = Mockito.mock(UserDAO.class);

        when(mockUserDAO.findAll()).thenReturn(expectedUsers);

        UserServiceImpl userService = new UserServiceImpl(mockUserDAO, new BCryptPasswordEncoder());

        List<User> retrievedUsers = userService.getAllUsers();
        assertEquals(expectedUserCount, retrievedUsers.size());

        for (int i = 0; i < expectedUserCount; i++) {
            assertEquals(expectedUsers.get(i), retrievedUsers.get(i));
        }
        verify(mockUserDAO).findAll();
    }

    @Test
    public void testDeleteUser() {
        long userId = 1L;

        UserDAO mockUserDAO = Mockito.mock(UserDAO.class);
        RoleDAO mockRoleDAO = Mockito.mock(RoleDAO.class);

        UserServiceImpl userService = new UserServiceImpl(mockUserDAO, new BCryptPasswordEncoder());
        userService.setRoleDAO(mockRoleDAO);

        userService.deleteUser(userId);
        verify(mockRoleDAO).deleteRolesByUserId(userId);
        verify(mockUserDAO).deleteById(userId);
    }

    @Test
    public void testFindByFirstName() {
        String firstName = "Ihor";
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Ihor", "Chubatenko"));
        users.add(new User(2, "Ihor", "Zapalskyi"));

        UserDAO mockUserDAO = Mockito.mock(UserDAO.class);
        when(mockUserDAO.findByFirstName(firstName)).thenReturn(users);

        UserServiceImpl userService = new UserServiceImpl(mockUserDAO, new BCryptPasswordEncoder());

        List<User> usersByName = userService.findByFirstName(firstName);
        verify(mockUserDAO).findByFirstName(firstName);

        assertEquals(users, usersByName);
    }

    @Test
    public void testGetUserByUsername() {
        long userId = 1L;
        String username = "iChubatenko";
        User expectedUser = new User(userId, "Ihor", "Chubatenko");
        expectedUser.setUsername(username);

        UserDAO mockUserDAO = Mockito.mock(UserDAO.class);
        when(mockUserDAO.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        UserServiceImpl userService = new UserServiceImpl(mockUserDAO, new BCryptPasswordEncoder());

        User retrievedUser = userService.findByUsername(username);
        verify(mockUserDAO).findByUsername(username);
        assertEquals(expectedUser, retrievedUser);
    }
}