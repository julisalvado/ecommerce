package com.uade.tpo.demo.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.UserRequest;
import com.uade.tpo.demo.exceptions.UserDuplicateException;
import com.uade.tpo.demo.service.UserServiceImpl;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> result = userService.getUserById(userId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserRequest userRequest)
            throws UserDuplicateException {
        User result = userService.createUser(
            userRequest.getUsername(),
            userRequest.getEmail(),
            userRequest.getPassword(),
            userRequest.getRole()
        );
        return ResponseEntity.created(URI.create("/users/" + result.getIdUsuario())).body(result);
    }

    @PutMapping("/{userId}/email")
    public ResponseEntity<User> updateUserEmail(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        Optional<User> result = userService.updateUserEmail(userId, userRequest.getEmail());
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.notFound().build();
    }
}