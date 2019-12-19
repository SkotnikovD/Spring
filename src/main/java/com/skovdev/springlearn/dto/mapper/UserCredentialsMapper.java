package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.dto.UserCredentialsDto;
import com.skovdev.springlearn.model.UserCredentials;

public class UserCredentialsMapper {
    public static UserCredentialsDto toDto(UserCredentials userCredentials) {
        return new UserCredentialsDto()
                .setLogin(userCredentials.getLogin())
                .setPassword(userCredentials.getPassword());
    }

    public static UserCredentials toModel(UserCredentialsDto userCredentialsDto) {
        return new UserCredentials()
                .setLogin(userCredentialsDto.getLogin())
                .setPassword(userCredentialsDto.getPassword());
    }
}
