package com.skovdev.springlearn.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class CredentialsDto {

    private String login;

    private String password;

    private Set<RoleDto> roles;
}
