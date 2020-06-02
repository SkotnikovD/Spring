package com.skovdev.springlearn.service;

import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentPrincipalInfoService {

    private UserRepository userRepository;

    @Autowired
    public CurrentPrincipalInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getCurrentUserLogin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal==null) throw new NullPointerException("Null was returned while trying to get the login of currently logged in user");
        return (String) principal;
    }

    public User getCurrentUser(){
        Optional<User> user = userRepository.getUser(getCurrentUserLogin());
        return user.orElseThrow(()->new RuntimeException("Sanity violation: current authorised user doest exists in database. Perhaps you're using invalid JWT auth token from other application's environment"));
    }
}
