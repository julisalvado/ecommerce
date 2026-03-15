package com.uade.tpo.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.UserRequest;
import com.uade.tpo.demo.exceptions.UserDuplicateException;
import com.uade.tpo.demo.service.UserService;

import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UsersController {
    private UserService userService;

    public UsersController() {
        userService = new UserService();
    }

    @GetMapping
    public ResponseEntity<ArrayList<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        Optional<User> result = userService.getUserById(userId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserRequest userRequest)
            throws UserDuplicateException {
        User result = userService.createUser(
            userRequest.getId(),
            userRequest.getUsername(),
            userRequest.getEmail(),
            userRequest.getPassword(),
            userRequest.getRole()
        );
        return ResponseEntity.created(URI.create("/users/" + result.getId())).body(result);
    }

    @PutMapping("/{userId}/email")
    public ResponseEntity<User> updateUserEmail(@PathVariable int userId, @RequestBody UserRequest userRequest) {
    Optional<User> result = userService.updateUserEmail(userId, userRequest.getEmail());
    if (result.isPresent())
        return ResponseEntity.ok(result.get());
    return ResponseEntity.notFound().build();
    }
}