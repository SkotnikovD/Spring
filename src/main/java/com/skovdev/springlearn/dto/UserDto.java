package com.skovdev.springlearn.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class UserDto {

    private String name;

    private Date birthday;

    //TODO add ability for user to add avatar

}
