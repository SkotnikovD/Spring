package com.skovdev.springlearn.security;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.dto.mapper.RoleMapper;
import com.skovdev.springlearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDto> userDto = userService.getUser(email, false);
        if (userDto.isPresent()) {
            List<GrantedAuthority> authorities = RoleMapper.toGrantedAuthorities(userDto.get().getRoles());
            return buildUserForAuthentication(userDto.get(), authorities);
        } else {
            throw new UsernameNotFoundException("User with email " + email + " does not exist.");
        }
    }

    private UserDetails buildUserForAuthentication(UserDto user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }
}
