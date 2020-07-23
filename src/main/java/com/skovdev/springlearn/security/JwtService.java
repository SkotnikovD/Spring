package com.skovdev.springlearn.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

import static com.skovdev.springlearn.dto.mapper.RoleMapper.toStringRoles;
import static com.skovdev.springlearn.security.SecurityConstants.AUTH_SECRET;
import static com.skovdev.springlearn.security.SecurityConstants.AUTH_TOCKEN_EXPIRATION_TIME_MS;

@Service
public class JwtService {

    public static final String ROLES_KEY = "roles";

    public String createJwtToken(org.springframework.security.core.userdetails.User forUser) {
        return createJwtToken(forUser.getUsername(), toStringRoles(forUser.getAuthorities()));
    }

    public String createJwtToken(String login, Set<String> roles) {
        Claims claims = Jwts.claims().setSubject(login);
        claims.put(ROLES_KEY, roles);
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + AUTH_TOCKEN_EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.HS512, AUTH_SECRET)
                .compact();
        return token;
    }

}
