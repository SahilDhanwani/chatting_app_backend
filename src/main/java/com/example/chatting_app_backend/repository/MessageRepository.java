package com.example.chatting_app_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.chatting_app_backend.data_packets.Responses.GetMessagesResponse;
import com.example.chatting_app_backend.model.messages;

@Repository
public interface MessageRepository extends JpaRepository<messages, Integer> {

    @Query("SELECT message, sender_id FROM messages WHERE (sender_id = ?1 OR sender_id = ?2) AND (receiver_id = ?2 OR receiver_id = ?1)")
    public List<GetMessagesResponse> findMessages(int user1, int user2);
}
