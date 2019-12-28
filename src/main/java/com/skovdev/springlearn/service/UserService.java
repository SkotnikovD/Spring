package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.dto.UserWithPasswordDto;

import java.util.Optional;

public interface UserService {
     UserDto registerNewUser(UserWithPasswordDto userWithPasswordDto);

     Optional<UserDto> getUser(String login);

}
