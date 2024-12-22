package com.example.chatting_app_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatting_app_backend.model.user;
import com.example.chatting_app_backend.services.service;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
@RequestMapping("/api")
public class HttpController {

    @Autowired
    service ser;

    @GetMapping("/allUsers")
    public List<user> getAllUsers() {
        return ser.getAllUsers();
    }

    @GetMapping("/allUsernames")
    public List<String> getAllUsernames() {
        return ser.getAllUsernames();
    }

    @PostMapping("/auth/signup")
    public boolean signUp(@RequestBody user user) {
        return ser.signup(user);
    }

    @PostMapping("/auth/login")
    public int login(@RequestBody user user) {
        return ser.login(user);
    }
}
