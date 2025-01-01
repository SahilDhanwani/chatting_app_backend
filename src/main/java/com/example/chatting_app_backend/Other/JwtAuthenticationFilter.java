package com.example.chatting_app_backend.Other;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter implements Filter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {

        // Cast ServletRequest to HttpServletRequest to get the Authorization header
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader("Authorization");

        // Check if the token is provided and is in the correct format
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Extract the token after "Bearer "

            // Validate the token and set the authentication in the SecurityContext
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(username, null, null); // No authorities here
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}
