package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Optional;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);

    Optional<UserDto> getUser(String login, boolean isExcludePass);

    UserDto getCurrentUser();

    URI addAvatar(MultipartFile avatar);
}
