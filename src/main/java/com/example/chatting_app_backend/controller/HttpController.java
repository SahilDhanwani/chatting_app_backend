package com.example.chatting_app_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatting_app_backend.data_packets.Requests.LoginRequest;
import com.example.chatting_app_backend.data_packets.Requests.SaveLastMessageRequest;
import com.example.chatting_app_backend.data_packets.Requests.SaveMessagesRequest;
import com.example.chatting_app_backend.data_packets.Requests.SignupRequest;
import com.example.chatting_app_backend.data_packets.Responses.ActiveChatsResponse;
import com.example.chatting_app_backend.data_packets.Responses.GetAllUsernameResponse;
import com.example.chatting_app_backend.data_packets.Responses.GetIdResponse;
import com.example.chatting_app_backend.data_packets.Responses.GetMessagesResponse;
import com.example.chatting_app_backend.data_packets.Responses.GetUsernameResponse;
import com.example.chatting_app_backend.services.service;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class HttpController {

    @Autowired
    service ser;

    @GetMapping("/allUsernames")
    public GetAllUsernameResponse getAllUsernames() {
        return ser.getAllUsernames();
    }

    @GetMapping("/getUsername")
    public GetUsernameResponse getUsername(ServletRequest request) {
        return ser.getUsername(request);
    }

    @GetMapping("/getIdByUsername")
    public GetIdResponse getId(@RequestParam String username) {
        return ser.getIdByUsername(username);
    }

    @GetMapping("/getId")
    public GetIdResponse getId(ServletRequest request) {
        return ser.getId(request);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest form_data) {
        try {
            if (ser.signup(form_data)) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest form_data, HttpServletResponse response) {
        return ser.login(form_data, response);
    }

    @GetMapping("/activeChats")
    public List<ActiveChatsResponse> getActiveChats(ServletRequest request) {
        return ser.getActiveChats(request);
    }

    @GetMapping("/getMessages")
    public List<GetMessagesResponse> getMessages(@RequestParam String user2, ServletRequest request) {
        return ser.getMessages(user2,request);
    }

    @PostMapping("/saveMessage")
    public void saveMessage(@RequestBody SaveMessagesRequest form_data, ServletRequest request) {
        ser.saveMessage(form_data, request);
    }

    @PostMapping("/saveLastMessage")
    public void saveLastMessage(@RequestBody SaveLastMessageRequest form_data, ServletRequest request) {
        ser.saveLastMessage(form_data, request);
    }

    @GetMapping("/validate")
    public GetIdResponse validate(ServletRequest request) {
        return ser.validate(request);
    }
}
