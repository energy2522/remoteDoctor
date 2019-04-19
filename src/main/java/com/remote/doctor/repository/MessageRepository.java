package com.remote.doctor.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.remote.doctor.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    List<Message> findByChat_Id(int chatId);

    List<Message> findByChat_IdAndAndSendDateAfter(int chatId, OffsetDateTime sendDate);

    Message findFirstByChat_IdOrderBySendDateAsc(int chatId);

}
