package com.skovdev.springlearn.model;

import com.google.common.collect.ImmutableMap;
import lombok.Value;

import java.util.Map;

@Value
public class Role {

    public static String ROLE_USER = "USER";
    public static String ROLE_ADMIN = "ADMIN";
    public static String ROLE_SUPER_ADMIN = "ROOT_ADMIN";

    private String role;

    public static Role of (String roleName){
        Role role = possibleRoles.get(roleName);
        if(role==null) throw new IllegalArgumentException("System doesn't support role '" + roleName + "'");
        return role;
    }

    private static Map<String, Role> possibleRoles = ImmutableMap.of(ROLE_USER, new Role(ROLE_USER), ROLE_ADMIN, new Role(ROLE_ADMIN), ROLE_SUPER_ADMIN, new Role(ROLE_SUPER_ADMIN));

}
