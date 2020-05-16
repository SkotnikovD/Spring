package com.skovdev.springlearn.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentPrincipalInfoService {

    public String getCurrentUserLogin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal==null) throw new NullPointerException("Null was returned while trying to get the login of currently logged in user");
        return (String) principal;
    }
}
