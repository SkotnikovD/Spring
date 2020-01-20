package com.skovdev.springlearn.security.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skovdev.springlearn.dto.CredentialsDto;
import com.skovdev.springlearn.dto.mapper.RoleMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.skovdev.springlearn.security.SecurityConstants.AUTH_HEADER_STRING;
import static com.skovdev.springlearn.security.SecurityConstants.AUTH_SECRET;
import static com.skovdev.springlearn.security.SecurityConstants.AUTH_TOCKEN_EXPIRATION_TIME;
import static com.skovdev.springlearn.security.SecurityConstants.AUTH_TOKEN_PREFIX;


public class ApiJWTSigninProcessingFilter extends UsernamePasswordAuthenticationFilter {
    public static final String ROLES_KEY = "roles";
    private AuthenticationManager authenticationManager;

    public ApiJWTSigninProcessingFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/signin", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            CredentialsDto credentialsDto = new ObjectMapper().readValue(req.getInputStream(), CredentialsDto.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentialsDto.getLogin(),
                            credentialsDto.getPassword(),
                            new ArrayList<>(RoleMapper.toGrantedAuthorities(credentialsDto.getRoles()))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        if (auth.getPrincipal() != null) {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
            String login = user.getUsername();
            Claims claims = Jwts.claims().setSubject(login);
            List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            claims.put(ROLES_KEY, roles);
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + AUTH_TOCKEN_EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, AUTH_SECRET)
                    .compact();
            res.addHeader(AUTH_HEADER_STRING, AUTH_TOKEN_PREFIX + token);
        }
    }
}
