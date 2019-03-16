package com.remote.doctor.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ClientDto {
    private int id;

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String phoneNumber;

    private MultipartFile avatar;

    private String encodeAvatar;
}
