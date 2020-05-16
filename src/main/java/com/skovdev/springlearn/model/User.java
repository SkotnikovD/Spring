package com.skovdev.springlearn.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class User {

    private Long userId;

    private String login;

    private String password;

    //TODO Validators on API input for NotNull and other logic
    private String firstName;

    private LocalDate birthdayDate;

    private String avatarFullsizeUrl;

    private String avatarThumbnailUrl;

    @Setter (AccessLevel.NONE)
    private Set<String> roles = new HashSet<>();

    @NotNull
    public User setRoles (String ...role){
        roles.addAll(Arrays.asList(role));
        return this;
    }

    @NotNull
    public User setRoles(String role){
        roles.add(role);
        return this;
    }
}
