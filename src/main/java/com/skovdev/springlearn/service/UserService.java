package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.user.GetFullUserDto;
import com.skovdev.springlearn.dto.user.SignUpUserDto;
import com.skovdev.springlearn.dto.user.UpdateUserDto;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.util.Optional;

public interface UserService {

    GetFullUserDto registerNewUser(SignUpUserDto signUpUserDto, @Nullable String thumbnailAvatarUrl, @Nullable String fullsizeAvatarUrl);

    Optional<GetFullUserDto> getUser(String login);

    GetFullUserDto updateCurrentUser(UpdateUserDto user);

    GetFullUserDto getCurrentUser();

    String addAvatar(MultipartFile avatar);
}
