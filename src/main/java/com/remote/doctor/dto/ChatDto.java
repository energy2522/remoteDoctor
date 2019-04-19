package com.remote.doctor.dto;

import lombok.Data;

@Data
public class ChatDto {
    private int id;
    private String encodeAvatar;
    private String fullName;
    private String lastMessage;
    private boolean isFromCurrentUser;
    private boolean isBlocked;
}
