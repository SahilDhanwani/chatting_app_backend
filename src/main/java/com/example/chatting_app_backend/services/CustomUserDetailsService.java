package com.example.chatting_app_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.chatting_app_backend.repository.repository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private repository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .stream()
            .findFirst()
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
