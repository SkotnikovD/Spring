package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.user.SignUpUserDto;
import com.skovdev.springlearn.dto.user.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {

    UserDto registerNewUser(SignUpUserDto signUpUserDto);

    Optional<UserDto> getUser(String login);

    UserDto updateUser(UserDto user);

    UserDto getCurrentUser();

    String addAvatar(MultipartFile avatar);
}
