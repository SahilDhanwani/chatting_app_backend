package com.example.chatting_app_backend.data_packets.Responses;

import java.util.List;

public class GetAllUsernameResponse {
    private List<String> usernames;

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
