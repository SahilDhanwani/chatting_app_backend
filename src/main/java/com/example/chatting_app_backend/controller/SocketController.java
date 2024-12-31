package com.example.chatting_app_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.chatting_app_backend.model.messages;

@Controller
public class SocketController {

    @Autowired
    private SimpMessagingTemplate smt;

    @MessageMapping("/send") // Maps to /app/send
    public void handleMessage(messages message) {
        // Send the message to the specific user's queue
        smt.convertAndSendToUser(String.valueOf(message.getReceiver_id()), "/messages", message);
    }
}
