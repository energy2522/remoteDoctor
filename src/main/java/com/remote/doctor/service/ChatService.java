package com.remote.doctor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.remote.doctor.domain.Chat;
import com.remote.doctor.domain.Client;
import com.remote.doctor.domain.Discussion;
import com.remote.doctor.domain.Doctor;
import com.remote.doctor.domain.Message;
import com.remote.doctor.domain.User;
import com.remote.doctor.dto.ChatDto;
import com.remote.doctor.dto.CopyWriter;
import com.remote.doctor.dto.MessageDto;
import com.remote.doctor.repository.ChatRepository;
import com.remote.doctor.repository.MessageRepository;
import com.remote.doctor.repository.UserRepository;
import com.remote.doctor.util.ImageUtils;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private DiscussionService discussionService;

    public Chat getChatByClientAndDoctor(int clientId, int doctorId) {
        return chatRepository.findByClient_IdAndAndDoctor_Id(clientId, doctorId);
    }

    @Transactional
    public Chat createChat(Client client, Doctor doctor) {
        Chat chat = new Chat();
        chat.setClient(client);
        chat.setDoctor(doctor);

        return chatRepository.save(chat);
    }

    public List<MessageDto> getMessagesByChatId(int chatId) {
        List<MessageDto> messages = messageRepository.findByChat_Id(chatId).stream()
                .map(message -> conversionService.convert(message, MessageDto.class)).sorted(Comparator.comparing(MessageDto::getSendDate)).collect(Collectors.toList());

        return messages;
    }

    public CopyWriter getSender(int principalId, int chatId) {
        Chat chat = chatRepository.findById(chatId).get();

        User user = chat.getClient();

        if (user.getId() != principalId) {
            user = chat.getDoctor();
        }

        return conversionService.convert(user, CopyWriter.class);
    }

    public CopyWriter getSender(int discussionId) {
        Chat chat = chatRepository.findById(discussionService.getById(discussionId).getChat().getId()).get();

        User user = chat.getClient();

        return conversionService.convert(user, CopyWriter.class);
    }

    public CopyWriter getRecipient(int principalId, int chatId) {
        Chat chat = chatRepository.findById(chatId).get();

        User user = chat.getClient();

        if (user.getId() == principalId) {
            user = chat.getDoctor();
        }

        return conversionService.convert(user, CopyWriter.class);
    }

    public CopyWriter getRecipient(int discussionId) {
        Chat chat = chatRepository.findById(discussionService.getById(discussionId).getChat().getId()).get();

        User user = chat.getClient();

        return conversionService.convert(user, CopyWriter.class);
    }

    public void saveMessage(MessageDto messageDto, int userId) {
        Message message = conversionService.convert(messageDto, Message.class);
        User user = userRepository.findById(userId).get();

        message.setUser(user);

        messageRepository.save(message);
    }


    public List<ChatDto> findAllByUserId(int userId) {
        List<ChatDto> result = new ArrayList<>();
        List<Chat> chats = chatRepository.findByClient_IdOrDoctor_Id(userId, userId);

        if (CollectionUtils.isEmpty(chats)) {
            return result;
        }

        result = chats.stream().map(chat -> convertToChatDto(chat, userId)).collect(Collectors.toList());

        for(ChatDto chatDto : result) {
            Message message = messageRepository.findFirstByChat_IdOrderBySendDateAsc(chatDto.getId());

            if (message == null) {
                chatDto.setLastMessage("Empty chat");
                continue;
            }

            chatDto.setFromCurrentUser(message.getUser().getId() == userId);
            chatDto.setLastMessage(message.getMessageText());
        }

        return result;
    }

    public List<MessageDto> getMessagesByDiscussionId(int discussionId) {
        Discussion discussion = discussionService.getById(discussionId);

        List<MessageDto> messages = messageRepository.findByChat_IdAndAndSendDateAfter(discussion.getChat().getId(), discussion.getSendDate()).stream()
                .map(message -> conversionService.convert(message, MessageDto.class)).sorted(Comparator.comparing(MessageDto::getSendDate)).collect(Collectors.toList());

        return messages;
    }

    private ChatDto convertToChatDto(Chat chat, int userId) {
        ChatDto chatDto = new ChatDto();
        byte[] image;
        String fullName;


        if (chat.getClient().getId() == userId) {
            image = chat.getDoctor().getAvatar();
            fullName = String.format("%s %s", chat.getDoctor().getLastname(), chat.getDoctor().getFirstname());
        } else {
            image = chat.getClient().getAvatar();
            fullName = String.format("%s %s", chat.getClient().getLastname(), chat.getClient().getFirstname());
        }

        chatDto.setId(chat.getId());
        chatDto.setEncodeAvatar(ImageUtils.encodeImage(image));
        chatDto.setFullName(fullName);
        chatDto.setBlocked(chat.isBlocked());

        return chatDto;
    }


}
