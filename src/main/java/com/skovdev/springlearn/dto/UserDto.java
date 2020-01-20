package com.skovdev.springlearn.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Set;

//TODO Should we use DTO at all?
// https://medium.com/walmartlabs/java-on-the-benefits-of-treating-dtos-as-magic-cookies-fd7d2e0207a5
@Getter
@Setter
@Accessors(chain = true)
public class UserDto {

    private String login;

    private String password;

    private String name;

    private Date birthdayDate;

    private Set<RoleDto> roles;

    //TODO add ability for user to add avatar

}
