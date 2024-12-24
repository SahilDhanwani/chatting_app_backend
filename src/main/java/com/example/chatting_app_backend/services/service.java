package com.example.chatting_app_backend.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatting_app_backend.model.messages;
import com.example.chatting_app_backend.model.user;
import com.example.chatting_app_backend.repository.LastMessageRepository;
import com.example.chatting_app_backend.repository.MessageRepository;
import com.example.chatting_app_backend.repository.repository;

@Service
public class service {

    @Autowired
    private repository repo;

    @Autowired
    private MessageRepository m_repo;

    @Autowired
    private LastMessageRepository lm_repo;

    // public void addLastMessage(lastMessage message) {
    //     lastMessageRepository.save(message);
    // }

    // public List<user> getAllUsers() {
    //     return repo.findAll();
    // }

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
        return m_repo.save(message);
    }

    public List<messages> getAllMessages() {
        return m_repo.findAll();
    }

    public List<String> getAllUsernames() {
       return repo.findAllUsernames();
    }

    public List<Object[]> getActiveChats(String username) {
        return lm_repo.findLastMessage(username);
    }

    public String getUsername(int id) {
        return repo.findById(id).get().getUsername();
    }

    public List<Object[]> getMessages(int user1, int user2) {
        return m_repo.findMessages(user1, user2);
    }

    public int getId(String username) {
        return repo.findByUsername(username).get(0).getId();
    }
}