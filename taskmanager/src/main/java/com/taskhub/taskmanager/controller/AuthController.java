package com.taskhub.taskmanager.controller;

import com.taskhub.taskmanager.config.JWTUtil;
import com.taskhub.taskmanager.model.User;
import com.taskhub.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {
    //here we will need to generate the jwt token after the user has loggedin
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    //once the user has loggedin we will generate a jwt token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        //toh yahan par authentication perform karna hai on user and password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
        //ab database se usernikalenge
        User dbUser=userRepository.findByUserName(user.getUserName())
                .orElseThrow(()->new RuntimeException("user not found"));
        //since we have authenticated the user and also extracted the same user from db now we can give a token
        String token= jwtUtil.generateToken(dbUser.getUserName(),dbUser.getRole());
        //once the token is generated we will return it
        return ResponseEntity.ok(token);
    }

}
