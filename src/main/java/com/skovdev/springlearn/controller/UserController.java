package com.skovdev.springlearn.controller;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public UserDto registerNewUser(@RequestBody UserDto user) {
        return userService.registerNewUser(user);
        //TODO Don't forget to integrate Exception Handler
    }

}
