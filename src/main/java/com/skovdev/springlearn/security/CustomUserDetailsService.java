package com.skovdev.springlearn.security;

import com.skovdev.springlearn.dto.mapper.RoleMapper;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUser(email);
        if (user.isPresent()) {
            List<GrantedAuthority> authorities = RoleMapper.toGrantedAuthorities(user.get().getRoles());
            return buildUserForAuthentication(user.get(), authorities);
        } else {
            throw new UsernameNotFoundException("User with email " + email + " does not exist.");
        }
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }
}
