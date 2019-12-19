package com.skovdev.springlearn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserWithCredentialsDto {

    @JsonProperty("user")
    private UserDto userDto;

    @JsonProperty("password")
    private String password;
}
