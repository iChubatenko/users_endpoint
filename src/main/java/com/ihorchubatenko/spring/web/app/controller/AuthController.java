package com.ihorchubatenko.spring.web.app.controller;

import com.ihorchubatenko.spring.web.app.dao.RoleDAO;
import com.ihorchubatenko.spring.web.app.dao.UserDAO;
import com.ihorchubatenko.spring.web.app.dto.RegisterDTO;
import com.ihorchubatenko.spring.web.app.entity.Role;
import com.ihorchubatenko.spring.web.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {

        if (userDAO.existsByUsername(registerDTO.getUsername())) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role roles = roleDAO.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userDAO.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }


}
