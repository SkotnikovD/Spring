package com.skovdev.springlearn.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class UserDto {

    private String login;

    private String name;

    private Date birthdayDate;

    private String avatarThumbnailUrl;

    private String avatarFullsizeUrl;
}
