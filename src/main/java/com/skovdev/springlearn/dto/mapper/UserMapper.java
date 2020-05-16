package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.dto.user.GetUserDto;
import com.skovdev.springlearn.dto.user.SignUpUserDto;
import com.skovdev.springlearn.dto.user.UpdateUserDto;
import com.skovdev.springlearn.model.User;

import java.util.Optional;

// TODO try to use http://modelmapper.org/getting-started/ for such straight-line conversations

public class UserMapper {
    public static GetUserDto toDto(User user) {
        return new GetUserDto()
                .setName(user.getFirstName())
                .setLogin(user.getLogin())
                .setBirthdayDate(user.getBirthdayDate())
                .setAvatarFullsizeUrl(user.getAvatarFullsizeUrl())
                .setAvatarThumbnailUrl(user.getAvatarThumbnailUrl())
                .setRoles(user.getRoles());
    }

    public static Optional<GetUserDto> toDto(Optional<User> user) {
        return user.map(UserMapper::toDto);
    }

    public static User toModel(UpdateUserDto updateUserDto) {
        return new User()
                .setFirstName(updateUserDto.getName())
                .setBirthdayDate(updateUserDto.getBirthdayDate())
                .setAvatarFullsizeUrl(updateUserDto.getAvatarFullsizeUrl())
                .setAvatarThumbnailUrl(updateUserDto.getAvatarThumbnailUrl());
    }

    public static User toModel(SignUpUserDto signUpUserDto) {
        return new User()
                .setFirstName(signUpUserDto.getName())
                .setLogin(signUpUserDto.getLogin())
                .setBirthdayDate(signUpUserDto.getBirthdayDate())
                .setPassword(signUpUserDto.getPassword());
    }


}
