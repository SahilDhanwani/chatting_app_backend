package com.example.chatting_app_backend.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

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
    
    public int extractUserId(String token) {
        // Create a Key object from the secret key (using HS512 algorithm)
        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        String subject = Jwts.parserBuilder()  // Use the new parserBuilder() method
                .setSigningKey(key)  // Use the SecretKey for validation
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return Integer.parseInt(subject); // Convert the subject (userId) back to an integer
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

    public String extractTokenFromCookie(ServletRequest request) {
        // Extract JWT from cookies
        String token = null;
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
            
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }
}
