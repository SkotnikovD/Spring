package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.model.User;

// TODO try to use http://modelmapper.org/getting-started/ for such straight-line conversations

public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto()
                .setBirthday(user.getBirthday())
                .setName(user.getName())
                .setLogin(user.getLogin());
    }

    public static User toModel(UserDto userDto) {
        return new User()
                .setName(userDto.getName())
                .setBirthday(userDto.getBirthday())
                .setLogin(userDto.getLogin());
    }
}
