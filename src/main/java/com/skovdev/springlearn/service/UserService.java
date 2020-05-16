package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.user.GetUserDto;
import com.skovdev.springlearn.dto.user.SignUpUserDto;
import com.skovdev.springlearn.dto.user.UpdateUserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {

    GetUserDto registerNewUser(SignUpUserDto signUpUserDto);

    Optional<GetUserDto> getUser(String login);

    GetUserDto updateCurrentUser(UpdateUserDto user);

    GetUserDto getCurrentUser();

    String addAvatar(MultipartFile avatar);
}
