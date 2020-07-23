package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.dto.user.GetFullUserDto;
import com.skovdev.springlearn.dto.user.GetUserDto;
import com.skovdev.springlearn.dto.user.SignUpUserDto;
import com.skovdev.springlearn.dto.user.UpdateUserDto;
import com.skovdev.springlearn.model.Role;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.model.google.GoogleUser;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

// TODO try to use http://modelmapper.org/getting-started/ for such straight-line conversations

public class UserMapper {
    public static GetUserDto toDto(User user) {
        return new GetUserDto()
                .setName(user.getFirstName())
                .setBirthdayDate(user.getBirthdayDate())
                .setAvatarFullsizeUrl(user.getAvatarFullsizeUrl())
                .setAvatarThumbnailUrl(user.getAvatarThumbnailUrl());
    }

    public static User toModel(UpdateUserDto updateUserDto, String login) {
        return new User()
                .setUserId(updateUserDto.getId())
                .setFirstName(updateUserDto.getName())
                .setBirthdayDate(updateUserDto.getBirthdayDate())
                .setAvatarFullsizeUrl(updateUserDto.getAvatarFullsizeUrl())
                .setAvatarThumbnailUrl(updateUserDto.getAvatarThumbnailUrl())
                .setLogin(login);
    }

    public static User toModel(SignUpUserDto signUpUserDto) {
        return new User()
                .setFirstName(signUpUserDto.getName())
                .setLogin(signUpUserDto.getLogin())
                .setBirthdayDate(signUpUserDto.getBirthdayDate())
                .setPassword(signUpUserDto.getPassword());
    }

    public static SignUpUserDto toDto(@NotNull GoogleUser googleUser, @NotNull String password) {
        return new SignUpUserDto()
                .setName(googleUser.getUserName())
                .setLogin(googleUser.getEmail())
                .setPassword(password);
    }

    public static GetFullUserDto toGetFullUserDto(User user) {
        return new GetFullUserDto()
                .setName(user.getFirstName())
                .setBirthdayDate(user.getBirthdayDate())
                .setAvatarFullsizeUrl(user.getAvatarFullsizeUrl())
                .setAvatarThumbnailUrl(user.getAvatarThumbnailUrl())
                .setRoles(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()));
    }

}
