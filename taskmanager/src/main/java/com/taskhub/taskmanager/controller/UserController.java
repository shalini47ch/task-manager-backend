package com.taskhub.taskmanager.controller;

import com.taskhub.taskmanager.model.User;
import com.taskhub.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {
    //yahan par signup ka functionality karenge aur password ko bcrypt ka use karke karenge
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    //ye signin ka functionality hai
    public ResponseEntity<?>register(@RequestBody User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if ("admin".equalsIgnoreCase(user.getUserName())) {
            user.setRole(String.valueOf(List.of("ROLE_ADMIN")));
        } else {
            user.setRole(String.valueOf(List.of("ROLE_USER")));
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");


    }


}
