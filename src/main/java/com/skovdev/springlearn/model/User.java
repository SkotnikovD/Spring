package com.skovdev.springlearn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class User {

    private String login;

    private String password;

    private String name;

    private Date birthday;

}
