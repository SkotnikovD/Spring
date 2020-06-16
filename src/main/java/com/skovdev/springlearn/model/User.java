package com.skovdev.springlearn.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @EqualsAndHashCode.Include
    @Column(name = "login", length = 250, nullable = false, unique = true)
    private String login;

    @Column(name = "password", length = 250, nullable = false)
    private String password;

    @Column(name = "first_name", length = 250, nullable = false)
    private String firstName;

    @Nullable
    @Column(name = "birthday_date")
    private LocalDate birthdayDate;

    @Column(name = "avatar_fullsize_url")
    private String avatarFullsizeUrl;

    @Column(name = "avatar_thumbnail_url")
    private String avatarThumbnailUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_to_user_roles",
            joinColumns = @JoinColumn(name = "user_id_fk", referencedColumnName="user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id_fk", referencedColumnName="user_role_id"))
    private Set<Role> roles = new HashSet<>();

    @NotNull
    public User addRole(Role role) {
        getRoles().add(role);
        return this;
    }

    public Set<String> getRolesAsStrings(){
        return getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());
    }
}
