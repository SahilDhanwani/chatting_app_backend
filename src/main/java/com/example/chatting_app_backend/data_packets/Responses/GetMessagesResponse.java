package com.example.chatting_app_backend.data_packets.Responses;

public class GetMessagesResponse {
    private String message;
    private int senderId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}
