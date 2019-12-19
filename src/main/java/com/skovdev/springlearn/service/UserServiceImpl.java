package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.dto.mapper.UserMapper;
import com.skovdev.springlearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto registerNewUser(UserDto userDto){
        return UserMapper.toUserDto(userRepository.createUser(UserMapper.toUser(userDto)));
    }
}
