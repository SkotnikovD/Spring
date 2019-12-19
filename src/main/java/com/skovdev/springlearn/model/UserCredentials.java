package com.skovdev.springlearn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserCredentials {

    private String login;

    private String password;
}
