package com.skovdev.springlearn.model;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class User {

    private long userId;

    private String login;

    private String password;

    //TODO Validators on API input for NotNull and other logic
    private String firstName;

    private Date birthdayDate;

    @Setter (AccessLevel.NONE)
    @Nullable
    private Set<Role> roles;

    public User setRoles (Role ...role){
        roles = ImmutableSet.copyOf(role);
        return this;
    }
}
