package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.dto.user.GetUserDto;
import com.skovdev.springlearn.dto.user.SignUpUserDto;
import com.skovdev.springlearn.dto.user.UpdateUserDto;
import com.skovdev.springlearn.model.Role;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.model.google.GoogleUser;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;
import java.util.stream.Collectors;

// TODO try to use http://modelmapper.org/getting-started/ for such straight-line conversations

public class UserMapper {
    public static GetUserDto toDto(User user) {
        return new GetUserDto()
                .setName(user.getFirstName())
                .setBirthdayDate(user.getBirthdayDate())
                .setAvatarFullsizeUrl(user.getAvatarFullsizeUrl())
                .setAvatarThumbnailUrl(user.getAvatarThumbnailUrl())
                .setRoles(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()));
    }

    public static Optional<GetUserDto> toDto(Optional<User> user) {
        return user.map(UserMapper::toDto);
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

    public static SignUpUserDto toDto(GoogleUser googleUser) {
        return new SignUpUserDto()
                .setName(googleUser.getUserName())
                .setLogin(googleUser.getEmail())
                //Hacky, but seems acceptable and secure. Ok as temporary solution
                .setPassword(RandomStringUtils.random(50, true, true));
    }

}
