package com.example.chatting_app_backend.data_packets.Responses;

public class GetMessagesResponse {
    private String message;
    private int sender_id;

    public GetMessagesResponse(String message, int sender_id) {
        this.message = message;
        this.sender_id = sender_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSenderId() {
        return sender_id;
    }

    public void setSenderId(int sender_id) {
        this.sender_id = sender_id;
    }
}
