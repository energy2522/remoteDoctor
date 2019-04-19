package com.remote.doctor.dto;

import lombok.Data;

@Data
public class MessageDto {
    private int chatId;
    private String toId;
    private String fromId;
    private String sendDate;
    private String messageText;
}
