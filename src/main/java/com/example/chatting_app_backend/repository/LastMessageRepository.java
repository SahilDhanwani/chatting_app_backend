package com.example.chatting_app_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.chatting_app_backend.model.lastMessage;

@Repository
public interface LastMessageRepository extends JpaRepository<lastMessage, Integer> {

    @Query("SELECT user2,lastMessage FROM lastMessage WHERE user1 = ?1")
    public List<Object[]> findLastMessage(String user1);
}
