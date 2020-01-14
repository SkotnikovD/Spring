package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.dto.RoleDto;
import com.skovdev.springlearn.model.Role;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleDto toDto(Role role){
        return new RoleDto().setRole(role.getRole());
    }

    public static Role toModel (RoleDto roleDto){
        return new Role(roleDto.getRole());
    }

    public static List<GrantedAuthority> toGrantedAuthorities(@Nullable Set<RoleDto> userRoles) {
        if(userRoles!=null) {
            return userRoles.stream().map(userRole->new SimpleGrantedAuthority(userRole.getRole())).collect(Collectors.toList());
        } else {
            return new ArrayList<GrantedAuthority>();
        }
    }
}
