package com.skovdev.springlearn.security.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.skovdev.springlearn.security.SecurityConstants.AUTH_HEADER_STRING;
import static com.skovdev.springlearn.security.SecurityConstants.AUTH_SECRET;
import static com.skovdev.springlearn.security.SecurityConstants.AUTH_TOKEN_PREFIX;
import static com.skovdev.springlearn.security.api.ApiJWTSigninProcessingFilter.ROLES_KEY;


//TODO It doesn't seem that BasicAuthenticationFilter is a good choice for REST security.
// Should try this: https://habr.com/ru/post/278411/
// and then try OAuth2
public class ApiJWTAuthenticationProcessingFilter extends BasicAuthenticationFilter {
    public ApiJWTAuthenticationProcessingFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(AUTH_HEADER_STRING);
        if (header == null || !header.startsWith(AUTH_TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER_STRING);
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(AUTH_SECRET)
                    .parseClaimsJws(token.replace(AUTH_TOKEN_PREFIX, ""))
                    .getBody();
            // Extract the UserName
            String user = claims.getSubject();
            Assert.notNull(user);
            // Extract the Roles
            List<String> roles = (List<String>) claims.get(ROLES_KEY);
            // Then convert Roles to GrantedAuthority Object for injecting
            List<GrantedAuthority> grantedAuthorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
        }
        return null;
    }
}
