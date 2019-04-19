package com.remote.doctor.converter;

import java.time.OffsetDateTime;

import org.springframework.core.convert.converter.Converter;

import com.remote.doctor.domain.Chat;
import com.remote.doctor.domain.Message;
import com.remote.doctor.dto.MessageDto;

public class MessageDtoToMessageConverter implements Converter<MessageDto, Message> {

    @Override
    public Message convert(MessageDto source) {
        Message message = new Message();

        Chat chat = new Chat();
        chat.setId(source.getChatId());

        message.setChat(chat);
        message.setSendDate(OffsetDateTime.now());
        message.setMessageText(source.getMessageText());

        return message;
    }
}
