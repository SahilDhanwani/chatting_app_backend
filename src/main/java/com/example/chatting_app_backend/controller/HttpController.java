package com.example.chatting_app_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.chatting_app_backend.responses.getUsernameResponse;
import com.example.chatting_app_backend.services.service;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
    public getUsernameResponse getUsername(ServletRequest request) {
        return ser.getUsername(request);
    }

    @GetMapping("/getId")
    public int getId(@RequestParam String username) {
        return ser.getId(username);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signUp(@RequestBody user user) {
        try {
            if (ser.signup(user)) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginResponse user, HttpServletResponse response) {
        return ser.login(user, response);
    }

    @GetMapping("/activeChats")
    public List<Object[]> getActiveChats(ServletRequest request) {
        return ser.getActiveChats(request);
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

    @GetMapping("/validate")
    public int validate(ServletRequest request) {
        return ser.validate(request);
    }
}
