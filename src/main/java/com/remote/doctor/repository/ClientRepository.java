package com.remote.doctor.repository;

import org.springframework.data.repository.CrudRepository;

import com.remote.doctor.domain.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {

}
