package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.exceptions.UserDuplicateException;
import com.uade.tpo.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User createUser(String username, String email, String password, String role)
            throws UserDuplicateException {
        List<User> users = userRepository.findAll();
        if (users.stream().anyMatch(u -> u.getUsername().equals(username)))
            throw new UserDuplicateException();
        return userRepository.save(new User(username, email, password, role));
    }
    
    public Optional<User> updateUserEmail(Long userId, String newEmail) {
        return userRepository.findById(userId).map(user -> {
            user.setEmail(newEmail);
        return userRepository.save(user);
        });
    }
}