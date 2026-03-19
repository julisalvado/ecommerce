package com.uade.tpo.demo.service;

import java.util.ArrayList;
import java.util.Optional;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.exceptions.UserDuplicateException;
import com.uade.tpo.demo.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public ArrayList<User> getUsers() {
        return userRepository.getUsers();
    }

    public Optional<User> getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    public User createUser(int id, String username, String email, String password, String role)
            throws UserDuplicateException {
        ArrayList<User> users = userRepository.getUsers();
        if (users.stream().anyMatch(u -> u.getId() == id && u.getUsername().equals(username)))
            throw new UserDuplicateException();
        return userRepository.createUser(id, username, email, password, role);
    }
    
    public Optional<User> updateUserEmail(int userId, String newEmail) {
    return userRepository.updateUserEmail(userId, newEmail);
}
}