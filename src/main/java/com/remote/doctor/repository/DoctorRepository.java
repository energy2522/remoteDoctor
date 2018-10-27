package com.remote.doctor.repository;

import com.remote.doctor.domain.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

    Doctor getDoctorByUsername(String username);
}
