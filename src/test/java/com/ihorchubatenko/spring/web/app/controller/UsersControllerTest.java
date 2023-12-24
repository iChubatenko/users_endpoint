package com.ihorchubatenko.spring.web.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class UsersControllerTest {

//    @Test
//    public void testGetUserByID() {
//        User user = new User(1, "Ihor", "Chubatenko");
//
//        UserRepository mockRepo = Mockito.mock(UserRepository.class);
//        Mockito.when(mockRepo.getUserById(1)).thenReturn(user);
//
//        UsersController testedInstance = new UsersController(mockRepo);
//
//        ResponseEntity<User> userByID = testedInstance.getUserByID(1L);
//
//        assertEquals(user, userByID.getBody());
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        List<User> users = new ArrayList<>();
//        users.add(new User(1, "Ihor", "Chubatenko"));
//        users.add(new User(2, "Serhii", "Zapalskiy"));
//
//        UserRepository mockRepo = Mockito.mock(UserRepository.class);
//        Mockito.when(mockRepo.getAllUsers()).thenReturn(users);
//
//        UsersController testedInstance = new UsersController(mockRepo);
//
//        ResponseEntity<List<User>> allUsers = testedInstance.getAllUsers();
//
//        assertEquals(users, allUsers.getBody());
//
//    }
//
//    @Test
//    public void testCreateUser() {
//        User testUser = new User(1, "Ihor", "Chubatenko");
//        UserRepository mockRepo = Mockito.mock(UserRepository.class);
//        Mockito.when(mockRepo.saveUser(any(User.class))).thenReturn(testUser);
//
//        UsersController testedInstance = new UsersController(mockRepo);
//
//        ResponseEntity<User> savedUser = testedInstance.createUser(testUser);
//
//        assertEquals(testUser, savedUser.getBody());
//    }
//
//    @Test
//    public void testUpdateUser() {
//        User testUser = new User(1, "Ihor", "Chubatenko");
//
//        UserRepository mockRepo = Mockito.mock(UserRepository.class);
//        Mockito.when(mockRepo.getUserById(1L)).thenReturn(testUser);
//        Mockito.when(mockRepo.saveUser(any(User.class))).thenReturn(testUser);
//        Mockito.when(mockRepo.getUserById(2L)).thenReturn(null);
//
//        UsersController testedInstance = new UsersController(mockRepo);
//
//        ResponseEntity<User> savedUser = testedInstance.updateUser(1L, testUser);
//
//        assertEquals(testUser, savedUser.getBody());
//
//    }
//
//    @Test
//    public void testDeleteUser() {
//        User testUser = new User(1, "Ihor", "Chubatenko");
//        UserRepository mockRepo = Mockito.mock(UserRepository.class);
//        Mockito.when(mockRepo.getUserById(1L)).thenReturn(testUser);
//        mockRepo.deleteUser(1L);
//        Mockito.when(mockRepo.getUserById(2L)).thenReturn(null);
//
//        Mockito.verify(mockRepo, Mockito.times(1)).deleteUser(1L);
//    }
}
