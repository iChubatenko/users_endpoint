package com.ihorchubatenko.spring.web.app.controller;

import com.ihorchubatenko.spring.web.app.dao.UserDAO;
import com.ihorchubatenko.spring.web.app.entity.User;
import com.ihorchubatenko.spring.web.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @GetMapping
    @Operation(summary = "Getting all users", description = "This endpoint retrieves all users from the database")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.debug("Received GET request to show list of all users");

        List<User> users = userService.getAllUsers();

        if (!users.isEmpty()) {
            logger.debug("Users were showed. Details: {}", users);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            logger.debug("No users found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    @Operation(summary = "Creating new user",
            description = "This endpoint creates and saves a new user in the database")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        logger.debug("Received POST request to create new user: {}", user);

        User savedUser = userService.saveUser(user);

        logger.debug("User created successfully. Details: id={} {}", user.getId(), savedUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userID}")
    @Operation(summary = "Getting user by ID",
            description = "This endpoint retrieves the user by their ID from the database")
    public ResponseEntity<User> getUserByID(@PathVariable(name = "userID") Long userID) {
        logger.debug("Received GET request to show user with id: {}", userID);

        User user = userService.getUserById(userID);

        if (user != null) {
            logger.debug("User was showed. Details: id={} {}", userID, user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            logger.debug("No user found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userID}")
    @Operation(summary = "Updating user",
            description = "This endpoint updates the user and saves changes to the database")
    public ResponseEntity<User> updateUser(@PathVariable(name = "userID") long userID, @RequestBody User updateUser) {
        User existingUser = userService.getUserById(userID);
        logger.debug("Received PUT request to update user with id: {} {}", userID, existingUser);

        if (existingUser != null) {
            existingUser.setFirstName(updateUser.getFirstName());
            existingUser.setLastName(updateUser.getLastName());
            existingUser.setUsername(updateUser.getUsername());
            existingUser.setPassword(updateUser.getPassword());

            User saveUser = userService.saveUser(existingUser);

            logger.debug("User updated successfully. Details: id={} {}", saveUser.getId(), saveUser);
            return new ResponseEntity<>(saveUser, HttpStatus.OK);
        } else {
            logger.debug("No user found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userID}")
    @Operation(summary = "Deleting user", description = "This endpoint deletes the user from the database")
    ResponseEntity<Void> deleteUser(@PathVariable(name = "userID") long userID) {
        logger.debug("Received DELETE request to delete user with id: {}", userID);
        User existingUser = userService.getUserById(userID);

        if (existingUser != null) {
            userService.deleteUser(userID);

            logger.debug("User deleted successfully.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.debug("No user found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Getting all users with the same name",
            description = "This endpoint retrieves the user with a search name from the database")
    public ResponseEntity<List<User>> showAllUsersByName(@PathVariable("name") String name) {
        logger.debug("Received GET request to show all users with name: {}", name);
        List<User> users = userService.findByFirstName(name);
        if (!users.isEmpty()) {
            logger.debug("Users were showed. Details: {}", users);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            logger.debug("No users found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

//    @GetMapping("/{username}")
//    public ResponseEntity<User> getUserDetails(@PathVariable String username){
//
//        User user = userDAO.findByUsername(username);
//
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

//    @PostMapping("/register")
//    public ResponseEntity<User> registerUser(@RequestBody Registration request){
//
//        User savedUser = userService.registerUser(request.getUsername(), request.getPassword());
//        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//    }
}
