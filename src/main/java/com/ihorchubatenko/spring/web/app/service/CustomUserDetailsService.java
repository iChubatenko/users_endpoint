package com.ihorchubatenko.spring.web.app.service;

import com.ihorchubatenko.spring.web.app.dao.UserDAO;
import com.ihorchubatenko.spring.web.app.entity.Role;
import com.ihorchubatenko.spring.web.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserDAO userDAO;

    @Autowired
    public CustomUserDetailsService(UserDAO userDAO) {
        System.out.println("Constructor starts CustomUserDetailsService");
        this.userDAO = userDAO;
        System.out.println("Constructor ends CustomUserDetailsService");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("method loadUserByUsername starts");
        User user = userDAO.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        System.out.println("method loadUserByUsername ends");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));

    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        System.out.println("method mapRolesToAuthorities starts");
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }
}
