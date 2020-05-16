package com.skovdev.springlearn.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateUserDto {

    private String name;

    private LocalDate birthdayDate;

    private String avatarThumbnailUrl;

    private String avatarFullsizeUrl;

}
