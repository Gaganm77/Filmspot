package com.example.filmspot.security.controller;

import com.example.filmspot.model.User;
import com.example.filmspot.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User users) {

        return userService.register(users);
    }

    @PostMapping("/login")
    public String login(@RequestBody User users) {
        return userService.verify(users);
    }
}
