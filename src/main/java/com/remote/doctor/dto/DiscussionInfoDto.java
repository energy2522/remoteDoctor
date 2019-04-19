package com.remote.doctor.dto;

import lombok.Data;

@Data
public class DiscussionInfoDto {
    private int id;
    private CopyWriter client;
    private CopyWriter doctor;
    private String comment;
}
