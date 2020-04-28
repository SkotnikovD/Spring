package com.skovdev.springlearn.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SignUpUserDto extends UserDto {
    String password;
}
