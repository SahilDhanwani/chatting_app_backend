package com.example.chatting_app_backend.data_packets.Requests;

public class LoginRequest {
    private String username;
    private String password;

    public String getUsername() {
        System.out.println(username);
        return username;
    }
    public String getPassword() {
        return password;
    }
}
