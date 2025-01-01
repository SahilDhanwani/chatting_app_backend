package com.example.chatting_app_backend.Other;

import io.jsonwebtoken.*;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret.key}")  // Spring will automatically inject the value from application.properties
    private String secretKey;

    private final long jwtExpirationMs = 3600000; // 1 hour

    public String generateToken(int userId) {
        // Create a Key object from the secret key (using HS512 algorithm)
        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setSubject(userId + "") // Convert the userId to a string
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key) // Use the SecretKey for signing
                .compact();
    }

    public String extractUsername(String token) {
        // Create a Key object from the secret key (using HS512 algorithm)
        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.parserBuilder()  // Use the new parserBuilder() method
                .setSigningKey(key)  // Use the SecretKey for validation
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            // Create a Key object from the secret key (using HS512 algorithm)
            Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName());

            Jwts.parserBuilder()  // Use the new parserBuilder() method
                    .setSigningKey(key)  // Use the SecretKey for validation
                    .build()
                    .parseClaimsJws(token);  // Validate the token by parsing it
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Token is invalid
        }
    }
}
