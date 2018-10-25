package com.remote.doctor.remote.doctor.repository;

import com.remote.doctor.remote.doctor.domain.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Integer> {
}
