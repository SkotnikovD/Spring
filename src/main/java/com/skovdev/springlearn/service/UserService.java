package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.UserDto;

import java.util.Optional;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);

    Optional<UserDto> getUser(String login, boolean isExcludePass);
}
