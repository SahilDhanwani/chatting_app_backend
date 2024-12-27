package com.example.chatting_app_backend.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(@SuppressWarnings("null") StompEndpointRegistry registry) {
        // Register WebSocket endpoint for SockJS
        registry.addEndpoint("/ws/chat") // Endpoint to connect to WebSocket
                .setAllowedOrigins("http://localhost:4200") // Allow Angular app to connect
                .withSockJS(); // Fall-back to SockJS if WebSocket is not supported
    }

    @Override
    public void configureMessageBroker(@SuppressWarnings("null") org.springframework.messaging.simp.config.MessageBrokerRegistry registry) {
        // Message broker configuration
        registry.enableSimpleBroker("/topic"); // Set up /topic for broadcasting messages
        registry.setApplicationDestinationPrefixes("/app"); // Prefix for application messages
    }
}
