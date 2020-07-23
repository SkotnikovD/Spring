package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.user.GetFullUserDto;
import com.skovdev.springlearn.dto.user.SignUpUserDto;
import com.skovdev.springlearn.dto.user.UpdateUserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {

    GetFullUserDto registerNewUser(SignUpUserDto signUpUserDto);

    Optional<GetFullUserDto> getUser(String login);

    GetFullUserDto updateCurrentUser(UpdateUserDto user);

    GetFullUserDto getCurrentUser();

    String addAvatar(MultipartFile avatar);
}
