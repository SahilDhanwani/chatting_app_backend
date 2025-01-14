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

import com.example.chatting_app_backend.jwt.JwtUtil;
import com.example.chatting_app_backend.model.lastMessage;
import com.example.chatting_app_backend.model.messages;
import com.example.chatting_app_backend.model.user;
import com.example.chatting_app_backend.repository.LastMessageRepository;
import com.example.chatting_app_backend.repository.MessageRepository;
import com.example.chatting_app_backend.repository.UserRepository;
import com.example.chatting_app_backend.responses.LoginResponse;
import com.example.chatting_app_backend.responses.getUsernameResponse;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class service {

    @Autowired
    private UserRepository user_repo;

    @Autowired
    private MessageRepository message_repo;

    @Autowired
    private LastMessageRepository last_message_repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public boolean signup(user user) {
        // Check if a user with the same username or email already exists
        int count = user_repo.findFirstByUsernameOrEmail(user.getUsername(), user.getEmail()).getId();
        if (count > 0) {
            return false; // User already exists
        }
    
        // Encode the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user_repo.save(user); // Save user to the database
        return true;
    }

    public ResponseEntity<?> login(@RequestBody LoginResponse form_data, HttpServletResponse response) {
        try {
            // Retrieve the user from the database by username or email
            user user = user_repo.findFirstByUsernameOrEmail(form_data.getUsername(), form_data.getUsername());

            // Authenticate the user using Spring Security's AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), form_data.getPassword())
            );

            // If authentication is successful, create and return the authentication token (JWT)
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtil.generateToken(user.getId());
            
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
        return message_repo.save(message);
    }

    public List<messages> getAllMessages() {
        return message_repo.findAll();
    }

    public List<String> getAllUsernames() {
       return user_repo.findAllUsernames();
    }

    public List<Object[]> getActiveChats(ServletRequest request) {
        String token = jwtUtil.extractTokenFromCookie(request);
        int userId = jwtUtil.extractUserId(token);
        String username = user_repo.findById(userId).get().getUsername();
        return last_message_repo.findLastMessage(username);
    }

    public getUsernameResponse getUsername(ServletRequest request) {
        int userId = jwtUtil.extractUserId(jwtUtil.extractTokenFromCookie(request));
        String name = user_repo.findById(userId).get().getUsername();
        return new getUsernameResponse(name);
    }

    public List<Object[]> getMessages(int user1, int user2) {
        return message_repo.findMessages(user1, user2);
    }

    public int getId(String username) {
        return user_repo.findByUsername(username).get(0).getId();
    }

    public void saveLastMessage(lastMessage lm) {

        lastMessage existingRecord = last_message_repo.findByUser1AndUser2(lm.getUser1(), lm.getUser2());

        if (existingRecord != null) {

            // Update the existing record
            existingRecord.setLastMessage(lm.getLastMessage());
            last_message_repo.save(existingRecord);
        } else {
            // Save as a new record 
            last_message_repo.save(lm);
        }
    }

    public int validate(ServletRequest request) {
        String token = jwtUtil.extractTokenFromCookie(request);
        return jwtUtil.extractUserId(token);
    }
}