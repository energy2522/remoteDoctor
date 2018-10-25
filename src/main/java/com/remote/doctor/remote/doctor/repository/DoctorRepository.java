package com.remote.doctor.remote.doctor.repository;

import com.remote.doctor.remote.doctor.domain.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
}
