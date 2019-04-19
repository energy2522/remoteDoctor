package com.remote.doctor.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import com.remote.doctor.constant.MessageConstants;
import com.remote.doctor.dto.MessageDto;
import com.remote.doctor.service.ChatService;
import com.remote.doctor.service.SecurityService;

@Controller
public class MessagingController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatService chatService;

    @Autowired
    private SecurityService securityService;

    @MessageMapping(MessageConstants.SECURED_CHAT_ROOM)
    public void sendSpecific(@Payload MessageDto msg, Principal principal, @Header("simpSessionId") String sessionId) {
        int currentUserId = securityService.getLoggedUserId(principal);

        chatService.saveMessage(msg, currentUserId);
        System.out.println("msg = " + msg);

        simpMessagingTemplate.convertAndSendToUser(String.valueOf(msg.getToId()), MessageConstants.SECURED_CHAT_SPECIFIC_USER, msg);
    }
}
