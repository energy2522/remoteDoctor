package com.remote.doctor.domain;

import lombok.Data;

import javax.persistence.*;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

@Data
@Entity
@Table(name = "doctor")
public class Doctor extends User{

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "city")
    private String city;

    @Column(name = "price")
    private double price;

    @Column(name = "amount")
    private double amount;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "rate", nullable = false)
    private double rate;

    @Column(name = "avatar")
    private byte[] avatar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private DoctorType doctorType;


}
