package com.remote.doctor.converter;

import org.springframework.core.convert.converter.Converter;

import com.remote.doctor.domain.Chat;
import com.remote.doctor.domain.Discussion;
import com.remote.doctor.dto.DiscussionDto;
import com.remote.doctor.util.DateTimeUtils;

public class DiscussionDtoToDiscussionConverter implements Converter<DiscussionDto, Discussion> {
    @Override
    public Discussion convert(DiscussionDto source) {
        Discussion discussion = new Discussion();

        Chat chat = new Chat();
        chat.setId(source.getChatId());

        discussion.setComment(source.getComment());
        discussion.setSendDate(DateTimeUtils.getStartDate(source.getStartDate()));
        discussion.setChat(chat);

        return discussion;
    }
}
