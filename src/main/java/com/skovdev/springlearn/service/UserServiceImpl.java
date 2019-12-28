package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.dto.UserWithPasswordDto;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.skovdev.springlearn.dto.mapper.UserMapper.toDto;
import static com.skovdev.springlearn.dto.mapper.UserMapper.toModel;

@Service
public class UserServiceImpl implements UserService {

    //TODO should I inject services by constructor, non by field?
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto registerNewUser(UserWithPasswordDto userWithPasswordDto) {
        //TODO check if the user is already exists
        User user = toModel(userWithPasswordDto.getUserDto());
        String passwordHash = bCryptPasswordEncoder.encode(userWithPasswordDto.getPassword());
        return toDto(userRepository.createUser(user, passwordHash));
    }

    @Override
    public Optional<UserDto> getUser(String login) {
        return toDto(userRepository.getUser(login));
    }
}
