package com.ihorchubatenko.spring.web.app.dao;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.Mockito.*;

public class UserDAOImplTest extends TestCase {

//    @Test
//    public void testSaveUser() {
//
//        User newUser = new User(1, "Ihor", "Chubatenko");
//
//        AtomicLong userIdGenerator = Mockito.mock(AtomicLong.class);
//        Map<Long, User> userDatabase = Mockito.mock(Map.class);
//
//        when(userIdGenerator.getAndIncrement()).thenReturn(1L);
//        when(userDatabase.containsKey(1L)).thenReturn(false);
//        when(userDatabase.put(1L, newUser)).thenReturn(null);

//        UserRepository mockRepo = new UserServiceImpl(userDatabase, userIdGenerator);
//
//        User savedUser = mockRepo.saveUser(newUser);
//
//        assertEquals(1L, savedUser.getId());
//        assertEquals("Ihor", savedUser.getName());
//        assertEquals("Chubatenko", savedUser.getSurname());
//
//
//        verify(userIdGenerator, times(1)).getAndIncrement();
//        verify(userDatabase, times(1)).containsKey(1L);
//        verify(userDatabase, times(1)).put(1L, savedUser);
//    }

//    @Test
//    public void testGetUserById() {
//        Map<Long, User> userDatabase = Mockito.mock(Map.class);
//        UserRepository mockRepo = Mockito.mock(UserRepository.class);
//        long userId = 1L;
//        User expectedUser = new User(userId, "Ihor", "Chubatenko");
//
//        when(mockRepo.getUserById(userId)).thenReturn(expectedUser);
//
//        User resultUser = mockRepo.getUserById(userId);
//
//        assertEquals(expectedUser, resultUser);
//
//        verify(mockRepo, times(1)).getUserById(userId);
//
//
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        UserRepository mockRepo = Mockito.mock(UserRepository.class);
//
//        User user1 = new User(1, "Ihor", "Chubatenko");
//        User user2 = new User(2, "Serhii", "Zapalskii");
//
//        when(mockRepo.getAllUsers()).thenReturn(Arrays.asList(user1, user2));
//
//        mockRepo.saveUser(user1);
//        mockRepo.saveUser(user2);
//
//        List<User> allUsers = mockRepo.getAllUsers();
//        assertNotNull(allUsers);
//
//        assertEquals(2, allUsers.size());
//
//        assertTrue(allUsers.contains(user1));
//        assertTrue(allUsers.contains(user2));
//
//    }
//
//    @Test
//    public void testDeleteUser() {
//        UserRepository mockRepo = Mockito.mock(UserRepository.class);
//        long userId = 1L;
//        User userToDelete = new User(userId, "Ihor", "Chubatenko");
//
//        when(mockRepo.deleteUser(userId)).thenReturn(userToDelete);
//
//        User deletedUser = mockRepo.deleteUser(userId);
//
//        verify(mockRepo, times(1)).deleteUser(userId);
//
//        assertEquals(userToDelete, deletedUser);
//    }
}