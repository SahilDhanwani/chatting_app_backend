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

import com.example.chatting_app_backend.data_packets.Requests.GetIdRequest;
import com.example.chatting_app_backend.data_packets.Requests.GetMessagesRequest;
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

    @GetMapping("/getId")
    public GetIdResponse getId(@RequestParam GetIdRequest form_data) {
        return ser.getId(form_data.getUsername());
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
    public List<GetMessagesResponse> getMessages(GetMessagesRequest form_data) {
        return ser.getMessages(form_data);
    }

    @PostMapping("/saveMessage")
    public void saveMessage(@RequestBody SaveMessagesRequest form_data) {
        ser.saveMessage(form_data);
    }

    @PostMapping("/saveLastMessage")
    public void saveLastMessage(@RequestBody SaveLastMessageRequest form_data) {

        ser.saveLastMessage(form_data);

        String temp = form_data.getUsername1();
        form_data.setUsername1(form_data.getUsername2());
        form_data.setUsername2(temp);

        ser.saveLastMessage(form_data);
    }

    @GetMapping("/validate")
    public GetIdResponse validate(ServletRequest request) {
        return ser.validate(request);
    }
}
