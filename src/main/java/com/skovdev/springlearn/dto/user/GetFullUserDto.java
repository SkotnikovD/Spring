package com.skovdev.springlearn.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class GetFullUserDto {

    private String login;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthdayDate;

    private String avatarThumbnailUrl;

    private String avatarFullsizeUrl;

    private Set<String> roles;
}
