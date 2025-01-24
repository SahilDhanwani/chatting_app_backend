package com.example.chatting_app_backend.data_packets.Responses;

public class ActiveChatsResponse {
    private String username;
    private String lastMessage;

    public ActiveChatsResponse(String username, String lastMessage) {
        this.username = username;
        this.lastMessage = lastMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}

