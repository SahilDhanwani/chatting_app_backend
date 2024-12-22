package com.example.chatting_app_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.chatting_app_backend.model.user;


@Repository
public interface  repository extends JpaRepository<user, Integer> {

    public List<user> findByUsernameOrEmail(String username, String email);

    @Query("SELECT u.username FROM user u")  // JPQL query
    public List<String> findAllUsernames();

}
