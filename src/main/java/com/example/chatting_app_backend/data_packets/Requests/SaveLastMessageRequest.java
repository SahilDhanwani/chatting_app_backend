package com.example.chatting_app_backend.data_packets.Requests;

public class SaveLastMessageRequest {
    private String username1;
    private String username2;
    private String lastMessage;

    public String getUsername1() {
        return username1;
    }
    public void setUsername1(String username1) {
        this.username1 = username1;
    }
    public String getUsername2() {
        return username2;
    }
    public void setUsername2(String username2) {
        this.username2 = username2;
    }
    public String getLastMessage() {
        return lastMessage;
    }
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
