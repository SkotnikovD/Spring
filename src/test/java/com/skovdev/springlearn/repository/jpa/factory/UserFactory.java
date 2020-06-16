package com.skovdev.springlearn.repository.jpa.factory;

import com.skovdev.springlearn.model.Role;
import com.skovdev.springlearn.model.User;

import java.time.LocalDate;

public class UserFactory {
    public static User allFieldsNoRoles() {
        LocalDate birthdayDate = LocalDate.of(2000, 12, 12);
        return new User()
                .setLogin("login")
                .setPassword("password")
                .setFirstName("firstname")
                .setAvatarFullsizeUrl("avatarFullUrl")
                .setAvatarThumbnailUrl("avatarThumbUrl")
                .setBirthdayDate(birthdayDate);
    }

    public static User allFieldsUserRole() {
        User user = allFieldsNoRoles();
        user.addRole(new Role().setRoleName(Role.ROLE_USER));
        return user;
    }

    public static User allFieldsAdminAndUserRoles() {
        User user = allFieldsUserRole();
        user.addRole(new Role().setRoleName(Role.ROLE_ADMIN));
        return user;
    }
}
