package com.skovdev.springlearn.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class UserCredentialsDto {

    private String login;

    private String password;

}
