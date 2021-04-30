package com.java.datageneratingsystem.controller;

import com.java.datageneratingsystem.entity.User;
import com.java.datageneratingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public List<User> getAllUserTest() {
        return userService.getUserTest();
    }
}
