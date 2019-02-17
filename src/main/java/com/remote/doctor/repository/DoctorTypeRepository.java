package com.remote.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.remote.doctor.domain.DoctorType;

@Repository
public interface DoctorTypeRepository extends JpaRepository<DoctorType, Integer> {

    DoctorType getByName(String name);
}
