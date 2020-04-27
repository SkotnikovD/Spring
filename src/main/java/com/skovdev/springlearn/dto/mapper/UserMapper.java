package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.model.User;

import java.util.Optional;
import java.util.stream.Collectors;

// TODO try to use http://modelmapper.org/getting-started/ for such straight-line conversations

public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto()
                .setBirthdayDate(user.getBirthdayDate())
                .setName(user.getFirstName())
                .setLogin(user.getLogin())
                //FIXME remove password from DTO!
                .setPassword(user.getPassword())
                .setRoles(user.getRoles() != null ? user.getRoles().stream().map(RoleMapper::toDto).collect(Collectors.toSet()) : null)
                .setAvatarFullsizeUrl(user.getAvatarFullsizeUrl())
                .setAvatarThumbnailUrl(user.getAvatarThumbnailUrl());
    }

    public static Optional<UserDto> toDto(Optional<User> user) {
        return user.map(UserMapper::toDto);
    }

    public static User toModel(UserDto userDto) {
        return new User()
                .setFirstName(userDto.getName())
                .setBirthdayDate(userDto.getBirthdayDate())
                .setLogin(userDto.getLogin())
        //.setRoles(userDto.getRoles().stream().map(RoleMapper::toModel).collect(Collectors.toSet())); //Security layer is responsible for role assigning.
                .setAvatarFullsizeUrl(userDto.getAvatarFullsizeUrl())
                .setAvatarThumbnailUrl(userDto.getAvatarThumbnailUrl());
    }

    public static User toModel(UserDto userDto, String password) {
        return toModel(userDto).setPassword(password);
    }


}
