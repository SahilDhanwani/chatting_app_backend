package com.example.chatting_app_backend.controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.example.chatting_app_backend.data_packets.WebSocketMessage;

@Controller
public class SocketController {

    private final SimpMessagingTemplate smt;

    public SocketController(SimpMessagingTemplate smt) {
        this.smt = smt;
    }

    @MessageMapping("/send") // Maps to /app/send
    public void handleMessage(WebSocketMessage form_data) {
        // Send the message to the specific user's queue
        String receiverId = String.valueOf(form_data.getReceiver_id());
        smt.convertAndSendToUser(receiverId, "/queue/messages", form_data);
    }
}
