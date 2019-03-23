package com.remote.doctor.repository;

import java.util.List;

import com.remote.doctor.domain.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

    List<Doctor> getAllByDoctorType_NameOrderByRateDesc(String type);
}
