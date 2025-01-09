package com.example.chatting_app_backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chatting_app_backend.Other.JwtUtil;
import com.example.chatting_app_backend.model.lastMessage;
import com.example.chatting_app_backend.model.messages;
import com.example.chatting_app_backend.model.user;
import com.example.chatting_app_backend.repository.LastMessageRepository;
import com.example.chatting_app_backend.repository.MessageRepository;
import com.example.chatting_app_backend.repository.repository;
import com.example.chatting_app_backend.responses.LoginResponse;
import com.example.chatting_app_backend.responses.getUsernameResponse;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class service {

    @Autowired
    private repository repo;

    @Autowired
    private MessageRepository m_repo;

    @Autowired
    private LastMessageRepository lm_repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public boolean signup(user user) {
        // Check if a user with the same username or email already exists
        int count = repo.findByUsernameOrEmail(user.getUsername(), user.getEmail()).size();
        if (count > 0) {
            return false; // User already exists
        }
    
        // Encode the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user); // Save user to the database
        return true;
    }

    public ResponseEntity<?> login(@RequestBody LoginResponse user, HttpServletResponse response) {
        try {
            // Retrieve the user from the database by username or email
            int userId = repo.findByUsernameOrEmail(user.getUsername(), user.getUsername()).get(0).getId();
            
            // Authenticate the user using Spring Security's AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            // If authentication is successful, create and return the authentication token (JWT)
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtil.generateToken(userId);
            
            // Create the HttpOnly cookie for the JWT
            Cookie cookie = new Cookie("JWT", jwtToken);
            cookie.setHttpOnly(true);  // Prevent JavaScript access to the token
            cookie.setSecure(false);  // Only send the cookie over HTTPS if true
            cookie.setPath("/");  // Make the cookie accessible across the whole domain
            cookie.setMaxAge(3600);  // Set cookie expiration (1 hour for example)

            // Add the cookie to the response
            response.addCookie(cookie);

            // Optionally, you can also return a success message or user info, but no need for the JWT in response body anymore.
            return ResponseEntity.ok().build();  // HTTP 200 OK status without body (JWT is in the cookie)

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    public messages saveMessage(messages message) {
        return m_repo.save(message);
    }

    public List<messages> getAllMessages() {
        return m_repo.findAll();
    }

    public List<String> getAllUsernames() {
       return repo.findAllUsernames();
    }

    public List<Object[]> getActiveChats(String username) {
        return lm_repo.findLastMessage(username);
    }

    public getUsernameResponse getUsername(ServletRequest request) {
        int userId = jwtUtil.extractUserId(jwtUtil.extractTokenFromCookie(request));
        String name = repo.findById(userId).get().getUsername();
        return new getUsernameResponse(name);
    }

    public List<Object[]> getMessages(int user1, int user2) {
        return m_repo.findMessages(user1, user2);
    }

    public int getId(String username) {
        return repo.findByUsername(username).get(0).getId();
    }

    public void saveLastMessage(lastMessage lm) {
        lastMessage existingRecord = lm_repo.findByUser1AndUser2(lm.getUser1(), lm.getUser2());

        if (existingRecord != null) {
            // Update the existing record
            existingRecord.setLastMessage(lm.getLastMessage());
            lm_repo.save(existingRecord);
        } else {
            // Save as a new record
            lm_repo.save(lm);
        }
    }
}