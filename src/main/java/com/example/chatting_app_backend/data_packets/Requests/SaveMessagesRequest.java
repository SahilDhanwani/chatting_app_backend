package com.example.chatting_app_backend.data_packets.Requests;

import java.time.LocalDateTime;

public class SaveMessagesRequest {
    private String message;
    private int sender_id;
    private int receiver_id;
    private LocalDateTime timestamp;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getSender_id() {
        return sender_id;
    }
    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }
    public int getReceiver_id() {
        return receiver_id;
    }
    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    
}
