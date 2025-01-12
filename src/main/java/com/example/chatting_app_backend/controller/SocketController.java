package com.example.chatting_app_backend.controller;

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
    public void handleMessage(messages message) {
        System.out.println("Received message: " + message);
    
        // Send the message to the specific user's queue
        String receiverId = String.valueOf(message.getReceiver_id());
        smt.convertAndSendToUser(receiverId, "/queue/messages", message);
    }
}
