package com.ihorchubatenko.spring.web.app.controller;

import com.ihorchubatenko.spring.web.app.entity.User;
import com.ihorchubatenko.spring.web.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<User> getUserByID(@PathVariable(name = "userID") Long userID) {
        User user = userService.getUserById(userID);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userID}")
    public ResponseEntity<User> updateUser(@PathVariable(name = "userID") long userID, @RequestBody User updateUser) {
        User existingUser = userService.getUserById(userID);

        if (existingUser != null) {
            existingUser.setName(updateUser.getName());
            existingUser.setSurname(updateUser.getSurname());

            User saveUser = userService.saveUser(existingUser);

            return new ResponseEntity<>(saveUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userID}")
    ResponseEntity<Void> deleteUser(@PathVariable(name = "userID") long userID) {
        User existingUser = userService.getUserById(userID);

        if (existingUser != null) {
            userService.deleteUser(userID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
