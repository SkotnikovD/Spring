package com.skovdev.springlearn.controller;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public UserDto getUser(@RequestParam("login") String login) {
        //TODO How to tell Jackson to treat null Optional as empty body?
        return userService.getUser(login).orElse(null);
        //TODO Don't forget to integrate Exception Handler
    }

}
