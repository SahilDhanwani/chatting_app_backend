package com.example.chatting_app_backend.configurations;

import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

// import jakarta.servlet.ServletRequest;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // @Autowired
    // private JwtUtil jwtUtil;

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

                        String query = request.getURI().getQuery();
                        if (query != null && query.contains("id=")) {
                            String id = query.split("id=")[1];
                            attributes.put("id", id); // Attach id to the WebSocket session
                            System.out.println("The id is received: " + id);
                            return true;
                        }
                        return false;
                        
                        // System.out.println("The request is received : "+ request);

                        // String token = jwtUtil.extractTokenFromCookie((ServletRequest) request);
                        // System.out.println("The token is received : "+ token);
                        // if (jwtUtil.validateToken(token)) {
                        //     String username = jwtUtil.extractUsername(token);
                        //     attributes.put("username", username); // Attach username to WebSocket session
                        //     return true; // Allow handshake
                        // }
                        // return false; // Reject handshake if token is invalid
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
        registry.enableSimpleBroker("/topic", "/queue"); // Use "/queue" for user-specific messages
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user"); // Prefix for user-specific destinations
    }
}
