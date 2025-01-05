package com.example.chatting_app_backend.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.chatting_app_backend.model.messages;

@Controller
public class SocketController {

    private final SimpMessagingTemplate smt;

    public SocketController(SimpMessagingTemplate smt) {
        this.smt = smt;
    }

    @MessageMapping("/send") // Maps to /app/send
    public void handleMessage(messages message, Principal principal) {
        // Use the authenticated user's principal name (username)
        // String senderUsername = principal.getName();

        // Send the message to the specific user's queue
        smt.convertAndSendToUser(String.valueOf(message.getReceiver_id()), "/messages", message);
    }
}
