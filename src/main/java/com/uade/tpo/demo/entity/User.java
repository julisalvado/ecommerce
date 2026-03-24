package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

    public User() {}

    public User(String username, String email, String password, String role) {
        this.username=username;
        this.email=email;
        this.password=password;
        this.role=role;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String role;
}