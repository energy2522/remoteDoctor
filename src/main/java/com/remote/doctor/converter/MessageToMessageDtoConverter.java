package com.remote.doctor.converter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

import com.remote.doctor.domain.Message;
import com.remote.doctor.dto.MessageDto;
import com.remote.doctor.util.DateTimeUtils;

public class MessageToMessageDtoConverter implements Converter<Message, MessageDto> {

    @Override
    public MessageDto convert(Message source) {
        if (source == null) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setChatId(source.getChat().getId());
        messageDto.setFromId(source.getUser().getUsername());
        messageDto.setMessageText(source.getMessageText());

        messageDto.setSendDate(DateTimeUtils.getCurrentTimeOrDate(source.getSendDate()));

        return messageDto;
    }
}
