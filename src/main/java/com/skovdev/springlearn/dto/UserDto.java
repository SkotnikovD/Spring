package com.skovdev.springlearn.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class UserDto {

    private String login;

    private String password;

    private String name;

    private Date birthdayDate;

    private Set<RoleDto> roles;

    private String avatarFullsizeUrl;

    private String avatarThumbnailUrl;
}
