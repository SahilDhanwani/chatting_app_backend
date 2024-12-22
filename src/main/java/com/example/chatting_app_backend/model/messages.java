package com.example.chatting_app_backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String message;
    private int sender_id;
    private int receiver_id;
    private LocalDateTime timestamp;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
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
    public String getTimestamp() {
        return timestamp.toString();
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
