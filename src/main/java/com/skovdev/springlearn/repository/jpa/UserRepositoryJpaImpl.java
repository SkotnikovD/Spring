package com.skovdev.springlearn.repository.jpa;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.google.common.collect.Sets;
import com.skovdev.springlearn.model.Role;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@Slf4j
@Repository
@Primary
@Transactional(propagation = Propagation.MANDATORY)
public class UserRepositoryJpaImpl implements UserRepository {

    private UserSpringDataRepository userSpringDataRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserRepositoryJpaImpl(UserSpringDataRepository userSpringDataRepository, RoleRepository roleRepository) {
        this.userSpringDataRepository = userSpringDataRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void createUser(User user) {
        Set<Role> roles = roleRepository.findByRoleNameIn(user.getRolesAsStrings());
        if (roles.size() != user.getRoles().size()) {
            throw new IllegalArgumentException("Trying to create user with unknown roles. This roles are unknown: " + Sets.symmetricDifference(user.getRoles(), roles));
        }
        user.setRoles(roles);
        userSpringDataRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUser(String login) {
        EntityGraph entityGraph = EntityGraphUtils.fromAttributePaths("roles");
        return Optional.ofNullable(userSpringDataRepository.findByLogin(login, entityGraph));
    }

    @Override
    public User updateUser(User userModel) {
        if(userModel.getUserId()==null) throw new IllegalArgumentException("Can't update user with null Id. For existed users Id field must always be filled");
        return userSpringDataRepository.save(userModel);
    }
}
