package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.dto.UserWithCredentialsDto;

import java.util.Optional;

public interface UserService {
     UserDto registerNewUser(UserWithCredentialsDto userDto);

     Optional<UserDto> getUser(String login);

}
