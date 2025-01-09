package com.example.chatting_app_backend.responses;

public class getUsernameResponse {
    private String username;

    public getUsernameResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
