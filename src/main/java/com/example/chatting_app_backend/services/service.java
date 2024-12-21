package com.example.chatting_app_backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatting_app_backend.model.user;
import com.example.chatting_app_backend.repository.repository;

@Service
public class service {

    @Autowired
    private repository repo;

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

    public boolean login(user user) {
        List<user> users = repo.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (users.isEmpty()) {
            return false;
        }
        user user1 = users.get(0);
        
        return user1.getPassword().equals(user.getPassword());
    }

}
