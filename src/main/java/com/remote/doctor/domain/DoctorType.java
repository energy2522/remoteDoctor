package com.remote.doctor.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "doctor_type")
public class DoctorType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
