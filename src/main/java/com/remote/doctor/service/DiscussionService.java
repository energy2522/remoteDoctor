package com.remote.doctor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.remote.doctor.domain.Chat;
import com.remote.doctor.domain.Discussion;
import com.remote.doctor.dto.DiscussionDto;
import com.remote.doctor.dto.DiscussionInfoDto;
import com.remote.doctor.repository.ChatRepository;
import com.remote.doctor.repository.DiscussionRepository;

@Service
public class DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private ChatRepository chatRepository;

    @Transactional
    public void openDiscussion(DiscussionDto discussionDto) {
        Discussion discussion = conversionService.convert(discussionDto, Discussion.class);
        discussionRepository.save(discussion);

        chatRepository.updateIsBlock(discussion.getChat().getId(), true);
    }

    @Transactional
    public List<DiscussionInfoDto> getAll() {
       return discussionRepository.findAll().stream()
               .map(discussion -> conversionService.convert(discussion, DiscussionInfoDto.class)).collect(Collectors.toList());
    }

    public Discussion getById(int id) {
        return discussionRepository.findById(id).orElse(null);
    }

    @Transactional
    public void acceptDiscussion(int discussionId) {
        Chat chat = discussionRepository.findById(discussionId).get().getChat();

        chatRepository.updateIsBlock(chat.getId(), false);

        discussionRepository.deleteById(discussionId);
    }

    @Transactional
    public void denyDiscussion(int discussionId) {
        Chat chat = discussionRepository.findById(discussionId).get().getChat();

        chatRepository.updateIsBlock(chat.getId(), false);

        discussionRepository.deleteById(discussionId);
    }
 }
