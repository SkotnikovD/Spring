package com.skovdev.springlearn.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
public class SignUpUserDto{

    private String login;

    private String name;

    private LocalDate birthdayDate;

    private String password;
}
