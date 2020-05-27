package com.skovdev.springlearn.model;

import com.google.common.collect.ImmutableSet;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@Accessors(chain = true)
@Immutable
@Entity
@Table(name="user_roles")
public class Role {

    //WARNING! If you change roles names, don't forget to change their string values in @Secured annotations in controllers!
    public static String ROLE_USER = "USER";
    public static String ROLE_ADMIN = "ADMIN";
    public static String ROLE_SUPER_ADMIN = "ROOT_ADMIN";
    private static Set<String> possibleRoles = ImmutableSet.of(ROLE_USER, ROLE_ADMIN, ROLE_SUPER_ADMIN);

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
//    @SequenceGenerator(name = "user_id_generator", sequenceName = "users_user_id_seq", allocationSize = 10)
    @Column(name = "user_role_id", nullable = false)
    private Long userRoleId;

    @Column(name = "role", nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles", fetch= FetchType.LAZY)
    private Set<User> users;

    private static boolean hasRole(String roleName) {
        return possibleRoles.contains(roleName);
    }

}
