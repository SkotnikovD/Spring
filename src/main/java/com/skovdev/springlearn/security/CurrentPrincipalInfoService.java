package com.skovdev.springlearn.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentPrincipalInfoService {

    public String getCurrentUserLogin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal==null) throw new NullPointerException("Null was returned while trying to get the login of currently logged in user");
        return (String) principal;
    }
}
