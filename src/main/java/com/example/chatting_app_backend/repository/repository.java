package com.example.chatting_app_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chatting_app_backend.model.user;


@Repository
public interface  repository extends JpaRepository<user, Integer> {

    List<user> findByUsernameOrEmail(String username, String email);

}
