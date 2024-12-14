package com.example.SpringSecurityTodoApp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.SpringSecurityTodoApp.entity.User;
import com.example.SpringSecurityTodoApp.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//    	log.info("Fetching user by email: {}", email);
        // Fetch the user by email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Validate role existence
        if (user.getRole() == null || user.getRole().getName() == null) {
            throw new UsernameNotFoundException("User role not found for email: " + email);
        }

        // Create authorities list
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));

        // Return UserDetails object
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),  // Use email for authentication
            user.getPassword(),  // Use the encrypted password
            authorities          // List of roles/authorities
        );
    }

    }
