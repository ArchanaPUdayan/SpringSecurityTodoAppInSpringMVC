package com.example.SpringSecurityTodoApp.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.example.SpringSecurityTodoApp.entity.User;
import com.example.SpringSecurityTodoApp.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        if (user != null) {
            request.getSession().setAttribute("userId", user.getId());
            request.getSession().setAttribute("role", user.getRole().getName());
            
            if (user.getRole().getName().equalsIgnoreCase("ADMIN")) {
                response.sendRedirect("/admin-page");
            } else if (user.getRole().getName().equalsIgnoreCase("USER")) {
                response.sendRedirect("/user-page"); 
            } else {
                response.sendRedirect("/error");
            }
        } else {
            response.sendRedirect("/login");
        }
    }

}
