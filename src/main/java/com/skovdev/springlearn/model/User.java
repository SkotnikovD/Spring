package com.skovdev.springlearn.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.annotation.Nullable;
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

    @Nullable
    private LocalDate birthdayDate;

    private String avatarFullsizeUrl;

    private String avatarThumbnailUrl;

//    @Setter(AccessLevel.NONE)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_to_user_roles",
            joinColumns = @JoinColumn(name = "user_id_fk", referencedColumnName="user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id_fk", referencedColumnName="user_role_id"))
    private Set<Role> roles = new HashSet<>();

//    @NotNull
//    public User setRoles(Role... role) {
//        getRoles().addAll(Arrays.asList(role));
//        return this;
//    }

    @NotNull
    public User addRole(Role role) {
        getRoles().add(role);
        return this;
    }
}
