package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.dto.UserWithCredentialsDto;
import com.skovdev.springlearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.skovdev.springlearn.dto.mapper.UserMapper.toModel;
import static com.skovdev.springlearn.dto.mapper.UserMapper.toDto;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto registerNewUser(UserWithCredentialsDto userWithCredentialsDto) {
        return toDto(userRepository.createUser(
                toModel(userWithCredentialsDto.getUserDto()),
                userWithCredentialsDto.getPassword()));
    }
}
