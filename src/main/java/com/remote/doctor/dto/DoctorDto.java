package com.remote.doctor.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class DoctorDto {
    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String description;

    private double price;

    private String city;

    private String type;

    private String phoneNumber;

    private MultipartFile avatar;
}
