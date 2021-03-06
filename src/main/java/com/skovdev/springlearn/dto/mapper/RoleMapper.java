package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.model.Role;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {
    public static List<GrantedAuthority> toGrantedAuthorities(@Nullable Set<Role> userRoles) {
        if (userRoles != null) {
            return userRoles.stream().map(r->new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
        } else {
            return new ArrayList<GrantedAuthority>();
        }
    }

    public static Set<String> toStringRoles(@Nullable Collection<GrantedAuthority> grantedAuthorities) {
        if (grantedAuthorities == null) return new HashSet<>();
        return grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

}
