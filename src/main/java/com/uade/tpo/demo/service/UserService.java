package com.uade.tpo.demo.service;

import java.util.ArrayList;
import java.util.Optional;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.exceptions.UserDuplicateException;

public interface UserService {

    public ArrayList<User> getUsers();

    public Optional<User> getUserById(int userId);

    public User createUser(int id, String username, String email, String password, String role)
            throws UserDuplicateException;
    
    public Optional<User> updateUserEmail(int userId, String newEmail);
}
