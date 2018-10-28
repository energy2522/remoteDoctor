package com.remote.doctor.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "username", nullable = false, unique = true, length = 45)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role_id")
    private Role role;

}