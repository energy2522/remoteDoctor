package com.remote.doctor.repository;

import org.springframework.data.repository.CrudRepository;

import com.remote.doctor.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User getByUsername(String username);
}
