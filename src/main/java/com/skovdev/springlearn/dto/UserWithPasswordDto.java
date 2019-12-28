package com.skovdev.springlearn.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class UserWithPasswordDto {

    private UserDto userDto;

    private String password;

}
