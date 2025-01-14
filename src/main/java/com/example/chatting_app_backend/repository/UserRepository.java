package com.example.chatting_app_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.chatting_app_backend.model.user;


@Repository
public interface  UserRepository extends JpaRepository<user, Integer> {

    @Query("SELECT u.username FROM user u")  // JPQL query
    public List<String> findAllUsernames();

    @Query("SELECT u FROM user u WHERE u.username = ?1 OR u.email = ?2")
    public user findFirstByUsernameOrEmail(String username, String email);

    public List<user> findByUsername(String username);

    public List<user> findByEmail(String email);
}
