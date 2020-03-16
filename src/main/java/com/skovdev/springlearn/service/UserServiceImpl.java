package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.model.Role;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.skovdev.springlearn.dto.mapper.UserMapper.toDto;
import static com.skovdev.springlearn.dto.mapper.UserMapper.toModel;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        //TODO check if the user is already exists
        User user = toModel(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setRoles(Role.of(Role.ROLE_USER));
        userRepository.createUser(user);
        return getUser(user.getLogin(), true).get();
    }

    @Override
    public Optional<UserDto> getUser(String login, boolean isExcludePass) {
        Optional<UserDto> userDto = toDto(userRepository.getUser(login));
        if (isExcludePass){userDto.ifPresent( user -> user.setPassword(null));}
        return userDto;
    }

    @Override
    public UserDto getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(username==null) return null;
        return this.getUser(username, true).orElse(null);
    }
}
