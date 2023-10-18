package com.ihorchubatenko.spring.web.app.controller;

import com.ihorchubatenko.spring.web.app.entity.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<User> getUserByID(@PathVariable Long userID) {
        User user = userRepository.getUserById(userID);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userID}")
    public ResponseEntity<User> updateUser(@PathVariable long userID, @RequestBody User updateUser) {
        User existingUser = userRepository.getUserById(userID);

        if (existingUser != null) {
            existingUser.setName(updateUser.getName());
            existingUser.setSurname(updateUser.getSurname());

            User saveUser = userRepository.saveUser(existingUser);

            return new ResponseEntity(saveUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @DeleteMapping("/{userID}")
    ResponseEntity<Void> deleteUser(@PathVariable long userID) {
        User existingUser = userRepository.getUserById(userID);

        if (existingUser != null) {
            userRepository.deleteUser(userID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
