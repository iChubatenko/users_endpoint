package com.ihorchubatenko.spring.web.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihorchubatenko.spring.web.app.dao.RoleDAO;
import com.ihorchubatenko.spring.web.app.dao.UserDAO;
import com.ihorchubatenko.spring.web.app.dto.RegisterDTO;
import com.ihorchubatenko.spring.web.app.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDAO userDAO;

    @MockBean
    private RoleDAO roleDAO;

    @Test
    public void testLoginWithValidCredentials() throws Exception {
        String requestBody = "{\"username\": \"validUsername\", \"password\": \"validPassword\"}";

        MvcResult result = mockMvc.perform(post("http://localhost:8080/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getHeader("Authorization");

        assertNotNull(token);
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        String requestBody = "{\"username\": \"invalidUsername\", \"password\": \"invalidPassword\"}";

        mockMvc.perform(post("http://localhost:8080/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User is not authorized"));
    }

    @Test
    public void testRegister_Success() throws Exception {

        RegisterDTO registerDTO = new RegisterDTO("username", "password", "firstName", "lastName");
        when(userDAO.existsByUsername(registerDTO.getUsername())).thenReturn(false);
        when(roleDAO.findByName("USER")).thenReturn(Optional.of(new Role()));

        mockMvc.perform(post("http://localhost:8080/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User registered success!")));
    }

    @Test
    public void testRegister_UsernameTaken() throws Exception {

        RegisterDTO registerDTO = new RegisterDTO("existingUsername", "password", "firstName", "lastName");
        when(userDAO.existsByUsername(registerDTO.getUsername())).thenReturn(true);

        mockMvc.perform(post("http://localhost:8080/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Username is taken")));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}