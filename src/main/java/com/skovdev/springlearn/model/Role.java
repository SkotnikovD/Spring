package com.skovdev.springlearn.model;

import com.google.common.collect.ImmutableSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

@Data
@Accessors(chain = true)
@Immutable
@Entity
@Table(name="user_roles")
@EqualsAndHashCode(exclude = {"userRoleId"})
@NoArgsConstructor
public class Role {

    //WARNING! If you change roles names, don't forget to change their string values in @Secured annotations in controllers!
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_SUPER_ADMIN = "ROOT_ADMIN";
    private static Set<String> possibleRoles = ImmutableSet.of(ROLE_USER, ROLE_ADMIN, ROLE_SUPER_ADMIN);

    @Id
    @Column(name = "user_role_id", nullable = false)
    private Integer userRoleId;

    @Column(name = "role", nullable = false, unique = true)
    private String roleName;

    private static boolean hasRole(String roleName) {
        return possibleRoles.contains(roleName);
    }

}
