package com.ihorchubatenko.spring.web.app.controller;

import com.ihorchubatenko.spring.web.app.dao.RoleDAO;
import com.ihorchubatenko.spring.web.app.dao.UserDAO;
import com.ihorchubatenko.spring.web.app.dto.AuthResponseDTO;
import com.ihorchubatenko.spring.web.app.dto.LoginDTO;
import com.ihorchubatenko.spring.web.app.dto.RegisterDTO;
import com.ihorchubatenko.spring.web.app.entity.Role;
import com.ihorchubatenko.spring.web.app.entity.User;
import com.ihorchubatenko.spring.web.app.security.JWTGenerator;
import com.ihorchubatenko.spring.web.app.dto.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private AuthenticationManager authenticationManager;
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserDAO userDAO, RoleDAO roleDAO,
                          PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    @Operation(summary = "Logging exist user",
            description = "This endpoint logs in, authenticates and authorizes the user in the system")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        logger.debug("Received POST request to login exist user: {}", loginDTO.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);

            logger.debug("User was authorized. Username:{}", loginDTO.getUsername());
            return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);

        } catch (AuthenticationException e) {
            ErrorResponseDTO errorResponse = new ErrorResponseDTO("User is not authorized");

            logger.debug("User wasn't authorized. Username:{}", loginDTO.getUsername());
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/register")
    @Operation(summary = "Registering new user",
            description = "This endpoint registers and saves a new user in the database")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        logger.debug("Received POST request to register new user: {}", registerDTO.getUsername());

        if (userDAO.existsByUsername(registerDTO.getUsername())) {

            logger.debug("The entered login already exists in the database. Username:{}", registerDTO.getUsername());
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());

        Optional<Role> optionalRole = roleDAO.findByName("USER");

        if (optionalRole.isPresent()) {
            user.setRoles(Collections.singletonList(optionalRole.get()));
        } else {
            Role userRole = new Role();
            userRole.setName("USER");
            roleDAO.save(userRole);
            user.setRoles(Collections.singletonList(userRole));
        }

        userDAO.save(user);

        logger.debug("The user is successfully registered. Username:{}", registerDTO.getUsername());
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

}
