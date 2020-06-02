package com.skovdev.springlearn.repository.jpa;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.skovdev.springlearn.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSpringDataRepository extends EntityGraphJpaRepository<User, Long> {
    User findByLogin(String login, EntityGraph entityGraph);
}
