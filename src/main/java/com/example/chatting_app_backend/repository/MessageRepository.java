package com.example.chatting_app_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chatting_app_backend.model.messages;

@Repository
public interface MessageRepository extends JpaRepository<messages, Integer> {
}
