package com.remote.doctor.converter;

import org.springframework.core.convert.converter.Converter;

import com.remote.doctor.domain.Discussion;
import com.remote.doctor.dto.DiscussionInfoDto;

public class DiscussionToDiscussionInfoDto implements Converter<Discussion, DiscussionInfoDto> {
    private UserToCopyWriterConverter copyWriterConverter;

    public DiscussionToDiscussionInfoDto(UserToCopyWriterConverter copyWriterConverter) {
        this.copyWriterConverter = copyWriterConverter;
    }

    @Override
    public DiscussionInfoDto convert(Discussion source) {
        DiscussionInfoDto discussionDto = new DiscussionInfoDto();

        discussionDto.setId(source.getId());
        discussionDto.setClient(copyWriterConverter.convert(source.getChat().getClient()));
        discussionDto.setDoctor(copyWriterConverter.convert(source.getChat().getDoctor()));
        discussionDto.setComment(source.getComment());

        return discussionDto;
    }
}
