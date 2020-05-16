package com.skovdev.springlearn.model;

import com.google.common.collect.ImmutableSet;
import lombok.Value;

import java.util.Set;

@Value
public class Role {

    //WARNING! If you change roles names, don't forget to change their string values in @Secured annotations in controllers!
    public static String ROLE_USER = "USER";
    public static String ROLE_ADMIN = "ADMIN";
    public static String ROLE_SUPER_ADMIN = "ROOT_ADMIN";

    private static boolean hasRole(String roleName) {
        return possibleRoles.contains(roleName);
    }

    private static Set<String> possibleRoles = ImmutableSet.of(ROLE_USER, ROLE_ADMIN, ROLE_SUPER_ADMIN);

}
