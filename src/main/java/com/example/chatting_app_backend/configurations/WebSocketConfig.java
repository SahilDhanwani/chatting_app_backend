package com.example.chatting_app_backend.configurations;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.example.chatting_app_backend.Other.JwtUtil;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtUtil jwtUtil;

    @SuppressWarnings("null")
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")
                .setAllowedOrigins("http://localhost:4200") // Allow Angular app to connect
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(
                        ServerHttpRequest request,
                        ServerHttpResponse response,
                        WebSocketHandler wsHandler,
                        Map<String, Object> attributes
                    ) {
                        System.out.println("Handshake intercepted");
                        // Extract token from query parameters
                        String token = request.getHeaders().getFirst("Authorization");

                        if (token != null && token.startsWith("Bearer ")) {
                            token = token.substring(7); // Remove "Bearer " prefix

                            System.out.println("Token: " + token);

                            if (jwtUtil.validateToken(token)) {
                                System.out.println("Token is valid");
                                String username = jwtUtil.extractUsername(token);
                                attributes.put("username", username); // Attach username to WebSocket attributes
                                return true; // Allow handshake
                            }
                        }
                        return false; // Reject handshake if token is invalid
                    }

                    @Override
                    public void afterHandshake(
                        ServerHttpRequest request,
                        ServerHttpResponse response,
                        WebSocketHandler wsHandler,
                        Exception exception
                    ) {
                        // Optional: Add post-handshake logic if needed
                    }
                })
                .withSockJS(); // Fallback to SockJS if WebSocket is not supported
    }

    @Override
    public void configureMessageBroker(@SuppressWarnings("null") MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Configure message broker for broadcasting messages
        registry.setApplicationDestinationPrefixes("/app"); // Prefix for application messages
    }
}
