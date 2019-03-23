package com.remote.doctor.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(name = "uid")
public class Client extends User{

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "amount")
    private double amount;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name = "avatar")
    private byte[] avatar;
}
