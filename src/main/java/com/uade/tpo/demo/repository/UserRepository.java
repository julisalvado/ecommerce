package com.uade.tpo.demo.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.uade.tpo.demo.entity.User;

public class UserRepository {
    private ArrayList<User> users;

    public UserRepository() {
        users = new ArrayList<>(Arrays.asList(
            User.builder().id(1).username("Test1").email("admin@mail.com")
                .password("1234").role("vendedor").build(),
            User.builder().id(2).username("juan123").email("juan@mail.com")
                .password("abcd").role("comprador").build()
        ));
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public Optional<User> getUserById(int userId) {
        return this.users.stream()
                .filter(u -> u.getId() == userId)
                .findAny();
    }

    public User createUser(int id, String username, String email, String password, String role) {
        User newUser = User.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .build();
        this.users.add(newUser);
        return newUser;
    }
    public Optional<User> updateUserEmail(int userId, String newEmail) {
    Optional<User> user = getUserById(userId);
    if (user.isPresent()) {
        user.get().setEmail(newEmail);
        return user;
    }
    return Optional.empty();
}
}