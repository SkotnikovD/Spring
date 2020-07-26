package com.skovdev.springlearn.repository.jpa;

import com.skovdev.springlearn.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface RoleRepository extends JpaRepository<Role, Integer> {
     Set<Role> findByRoleNameIn(Set<String> roleName);
}
