package com.example.chatting_app_backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatting_app_backend.model.lastMessage;
import com.example.chatting_app_backend.model.messages;
import com.example.chatting_app_backend.model.user;
import com.example.chatting_app_backend.responses.LoginResponse;
import com.example.chatting_app_backend.services.service;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
@RequestMapping("/api")
public class HttpController {

    @Autowired
    service ser;

    @GetMapping("/allUsernames")
    public List<String> getAllUsernames() {
        return ser.getAllUsernames();
    }

    @GetMapping("/getUsername")
    public Map<String, String> getUsername(int id) {
        Map<String, String> response = new HashMap<>();
        response.put("username", ser.getUsername(id));
        return response;
    }

    @GetMapping("/getId")
    public int getId(@RequestParam String username) {
        return ser.getId(username);
    }

    @PostMapping("/auth/signup")
    public boolean signUp(@RequestBody user user) {
        return ser.signup(user);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginResponse user) {
        return ser.login(user);
    }

    @GetMapping("/activeChats")
    public List<Object[]> getActiveChats(String username) {
        return ser.getActiveChats(username);
    }

    @GetMapping("/getMessages")
    public List<Object[]> getMessages(int user1, int user2) {
        return ser.getMessages(user1, user2);
    }

    @PostMapping("/saveMessage")
    public void saveMessage(@RequestBody messages m) {
        ser.saveMessage(m);
    }

    @PostMapping("/saveLastMessage")
    public void saveLastMessage(@RequestBody lastMessage lm) {
        ser.saveLastMessage(lm);
    }
}
