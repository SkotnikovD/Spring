package com.skovdev.springlearn.security.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skovdev.springlearn.dto.CredentialsDto;
import com.skovdev.springlearn.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.skovdev.springlearn.security.SecurityConstants.AUTH_HEADER_STRING;
import static com.skovdev.springlearn.security.SecurityConstants.AUTH_TOKEN_PREFIX;


public class ApiJWTSigninProcessingFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public ApiJWTSigninProcessingFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
                            null));
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
            String jwtToken = jwtService.createJwtToken(user);
            res.addHeader(AUTH_HEADER_STRING, AUTH_TOKEN_PREFIX + jwtToken);
        }
    }


}
