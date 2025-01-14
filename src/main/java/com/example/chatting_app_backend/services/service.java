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

import com.example.chatting_app_backend.data_packets.Requests.GetMessagesRequest;
import com.example.chatting_app_backend.data_packets.Requests.LoginRequest;
import com.example.chatting_app_backend.data_packets.Requests.SaveLastMessageRequest;
import com.example.chatting_app_backend.data_packets.Requests.SaveMessagesRequest;
import com.example.chatting_app_backend.data_packets.Requests.SignupRequest;
import com.example.chatting_app_backend.data_packets.Responses.ActiveChatsResponse;
import com.example.chatting_app_backend.data_packets.Responses.GetAllUsernameResponse;
import com.example.chatting_app_backend.data_packets.Responses.GetIdResponse;
import com.example.chatting_app_backend.data_packets.Responses.GetMessagesResponse;
import com.example.chatting_app_backend.data_packets.Responses.GetUsernameResponse;
import com.example.chatting_app_backend.jwt.JwtUtil;
import com.example.chatting_app_backend.model.lastMessage;
import com.example.chatting_app_backend.model.messages;
import com.example.chatting_app_backend.model.user;
import com.example.chatting_app_backend.repository.LastMessageRepository;
import com.example.chatting_app_backend.repository.MessageRepository;
import com.example.chatting_app_backend.repository.UserRepository;

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

    public boolean signup(SignupRequest form_data) {
        // Check if a user with the same username or email already exists
        int count = user_repo.findFirstByUsernameOrEmail(form_data.getUsername(), form_data.getEmail()).getId();

        if (count > 0) return false; // User already exists
        
        // Encode the user's password before saving
        user user = new user();
        user.setUsername(form_data.getUsername());
        user.setPassword(passwordEncoder.encode(form_data.getPassword()));
        user.setEmail(form_data.getEmail());

        user_repo.save(user); // Save user to the database
        return true;
    }

    public ResponseEntity<?> login(@RequestBody LoginRequest form_data, HttpServletResponse response) {
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

    public messages saveMessage(SaveMessagesRequest form_data) {

        messages message = new messages();
        message.setSender_id(form_data.getSender_id());
        message.setReceiver_id(form_data.getReceiver_id());
        message.setMessage(form_data.getMessage());
        message.setTimestamp(form_data.getTimestamp());

        return message_repo.save(message);
    }

    public GetAllUsernameResponse getAllUsernames() {
        GetAllUsernameResponse response = new GetAllUsernameResponse();
        response.setUsernames(user_repo.findAllUsernames());
        return response;
    }

    public List<ActiveChatsResponse> getActiveChats(ServletRequest request) {
        String token = jwtUtil.extractTokenFromCookie(request);
        int userId = jwtUtil.extractUserId(token);
        String username = user_repo.findById(userId).get().getUsername();
        return last_message_repo.findLastMessage(username);
    }

    public GetUsernameResponse getUsername(ServletRequest request) {
        int userId = jwtUtil.extractUserId(jwtUtil.extractTokenFromCookie(request));
        String name = user_repo.findById(userId).get().getUsername();

        GetUsernameResponse response = new GetUsernameResponse();
        response.setUsername(name);
        return response;
    }

    public List<GetMessagesResponse> getMessages(GetMessagesRequest form_data) {
        return message_repo.findMessages(form_data.getUser1_id(), form_data.getUser2_id());
    }

    public GetIdResponse getId(String username) {
        GetIdResponse response = new GetIdResponse();
        response.setId(user_repo.findByUsername(username).get(0).getId());
        return response;
    }

    public void saveLastMessage(SaveLastMessageRequest form_data) {

        lastMessage existingRecord = last_message_repo.findByUser1AndUser2(form_data.getUsername1(), form_data.getUsername2());

        if (existingRecord != null) {

            // Update the existing record
            existingRecord.setLastMessage(form_data.getLastMessage());
            last_message_repo.save(existingRecord);
        } else {
            // Save as a new record
            lastMessage message = new lastMessage();
            message.setUser1(form_data.getUsername1());
            message.setUser2(form_data.getUsername2());
            message.setLastMessage(form_data.getLastMessage());

            last_message_repo.save(message);
        }
    }

    public GetIdResponse validate(ServletRequest request) {
        String token = jwtUtil.extractTokenFromCookie(request);
        GetIdResponse response = new GetIdResponse();
        response.setId(jwtUtil.extractUserId(token));
        return response;
    }
}