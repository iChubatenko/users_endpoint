package com.ihorchubatenko.spring.web.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihorchubatenko.spring.web.app.configuration.MyConfiguration;
import com.ihorchubatenko.spring.web.app.entity.User;
import com.ihorchubatenko.spring.web.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ContextConfiguration(classes = MyConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @Test
    public void createUserReturnsCreatedWithValidUser() throws Exception {
        User userToCreate = new User(0, "Ihor", "Chubatenko");
        User createdUser = new User(1L, "Ihor", "Chubatenko");

        when(userService.saveUser(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToCreate)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(createdUser.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(createdUser.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(createdUser.getSurname()));

        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    public void createUserReturnsBadRequestWithInvalidUser() throws Exception {
        User invalidUser = new User(null, null);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    public void getAllUsersShouldReturnsAllUsers() throws Exception {
        User user1 = new User(1L, "Ihor", "Chubatenko");
        User user2 = new User(2L, "Serhii", "Zapalskii");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Ihor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].surname").value("Chubatenko"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Serhii"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].surname").value("Zapalskii"))
                .andReturn();

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void getUserByIDReturnsUserWhenUserExists() throws Exception {
        long userID = 1L;
        User mockUser = new User(userID, "Ihor", "Chubatenko");

        when(userService.getUserById(userID)).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userID}", userID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"name\": \"Ihor\", \"surname\": \"Chubatenko\"}"));

        verify(userService).getUserById(userID);
    }

    @Test
    public void getUserByIDReturnsNotFoundWhenUserDoesNotExist() throws Exception {
        long userID = 1;

        when(userService.getUserById(userID)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userID}", userID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(userService).getUserById(userID);
    }

    @Test
    public void updateUserSuccess() throws Exception {
        long userId = 1L;
        User existingUser = new User(userId, "Ihor", "Chubatenko");
        User updateUser = new User(userId, "UpdatedName", "UpdatedSurname");

        when(userService.getUserById(userId)).thenReturn(existingUser);
        when(userService.saveUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userID}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("UpdatedName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("UpdatedSurname"));

        verify(userService, times(1)).getUserById(userId);
        verify(userService, times(1)).saveUser(existingUser);
    }

    @Test
    public void updateUserNotFound() throws Exception {
        long userId = 1L;
        User updateUser = new User(userId, "UpdatedName", "UpdatedSurname");

        when(userService.getUserById(userId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userID}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(userService, times(1)).getUserById(userId);
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    public void deleteUserSuccess() throws Exception {
        long userId = 1L;
        User existingUser = new User(userId, "Ihor", "Chubatenko");

        when(userService.getUserById(userId)).thenReturn(existingUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userID}", userId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(userService, times(1)).getUserById(userId);
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    public void deleteUserNotFound() throws Exception {
        long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userID}", userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(userService, times(1)).getUserById(userId);
        verify(userService, never()).deleteUser(userId);
    }

    @Test
    public void showAllUsersByName() throws Exception {
        List<User> expectedUsers = Arrays.asList(
                new User(1L, "Ihor", "Chubatenko"),
                new User(2L, "Serhii", "Zapalskii")
        );

        when(userService.findByName("TestName")).thenReturn(expectedUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/name/{name}", "TestName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Ihor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Serhii"));

        verify(userService, times(1)).findByName("TestName");
    }
}
