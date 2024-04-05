package com.ihorchubatenko.spring.web.app.service;

import com.ihorchubatenko.spring.web.app.dao.UserDAO;
import com.ihorchubatenko.spring.web.app.entity.Role;
import com.ihorchubatenko.spring.web.app.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static junit.framework.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomUserDetailsServiceTest {

    @Test
    public void loadUserByUsername_UserFound_ReturnsUserDetails() {

        String username = "testUser";
        UserDAO userDAO = mock(UserDAO.class);
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(userDAO);
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        Role role = new Role();
        role.setName("USER");
        user.setRoles(Collections.singletonList(role));

        when(userDAO.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(role.getName())));
    }

    @Test
    public void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
        String username = "nonExistingUser";

        UserDAO userDAO = mock(UserDAO.class);
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(userDAO);

        when(userDAO.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));
    }
}
