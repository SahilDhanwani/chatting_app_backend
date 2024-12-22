package com.example.chatting_app_backend.controller;

import com.example.chatting_app_backend.model.messages;
import com.example.chatting_app_backend.services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @Autowired
    service ser;

    @MessageMapping("/send") // Maps to /app/send
    @SendTo("/topic/messages") // Broadcasts to /topic/messages
    public messages handleMessage(messages message) {
        // Save message to database
        ser.saveMessage(message);

        // Return the message to be broadcasted
        return message;
    }
}
