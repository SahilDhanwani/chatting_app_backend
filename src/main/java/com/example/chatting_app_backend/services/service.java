package com.example.chatting_app_backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatting_app_backend.model.messages;
import com.example.chatting_app_backend.model.user;
import com.example.chatting_app_backend.repository.MessageRepository;
import com.example.chatting_app_backend.repository.repository;

@Service
public class service {

    @Autowired
    private repository repo;

    @Autowired
    private MessageRepository messageRepository;

    public List<user> getAllUsers() {
        return repo.findAll();
    }

    public List<user> findByUsernameOrEmail(String username, String email) {
        return repo.findByUsernameOrEmail(username, email);
    }

    public boolean signup(user user) {
        int count = repo.findByUsernameOrEmail(user.getUsername(), user.getEmail()).size();
        if (count > 0) {
            return false;
        }
        repo.save(user);
        return true;
    }

    public int login(user user) {
        List<user> users = repo.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (users.isEmpty()) {
            return 0;
        }
        user user1 = users.get(0);
        
        return user1.getPassword().equals(user.getPassword())? user1.getId(): 0;
    }

    public messages saveMessage(messages message) {
        return messageRepository.save(message);
    }

    public List<messages> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<String> getAllUsernames() {
       return repo.findAllUsernames();
    }

}
