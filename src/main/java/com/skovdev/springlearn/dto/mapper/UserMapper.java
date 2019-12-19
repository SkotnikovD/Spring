package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.model.User;

// TODO try to use http://modelmapper.org/getting-started/ for such straight-line conversations

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setBirthday(user.getBirthday())
                .setName(user.getName());
    }

    public static User toUser(UserDto userDto) {
        return new User()
                .setName(userDto.getName())
                .setBirthday(userDto.getBirthday());
    }
}
