package com.remote.doctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.remote.doctor.domain.Chat;

public interface ChatRepository extends CrudRepository<Chat, Integer> {

    Chat findByClient_IdAndAndDoctor_Id(int clientId, int doctorId);

    List<Chat> findByClient_IdOrDoctor_Id(int clientId, int doctorId);

    @Modifying
    @Query("update Chat c set c.isBlocked = :isBlocked where c.id = :chatId")
    int updateIsBlock(@Param("chatId") int chatId, @Param("isBlocked") boolean isBlocked);
}
