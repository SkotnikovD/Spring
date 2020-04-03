package com.skovdev.springlearn.controller;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.error.exceptions.NoSuchObjectException;
import com.skovdev.springlearn.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Log
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public UserDto getUser(@RequestParam("login") String login) {
        if (login.equals("np"))
            throw  new  NullPointerException("test Null Pointer");
        Optional<UserDto> user = userService.getUser(login, true);
        return user.orElseThrow(()-> new NoSuchObjectException("There is no user with login = " + login));
    }

    @PostMapping()
    @RequestMapping("/signup")
    public UserDto createUser(@RequestBody UserDto userDto){
        return userService.registerNewUser(userDto);
    }

    @GetMapping("/current")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    }
