package com.skovdev.springlearn.repository.jpa;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.skovdev.springlearn.model.Post;
import org.springframework.stereotype.Repository;

@Repository
interface PostSpringDataRepository extends EntityGraphJpaRepository<Post, Long> {
}
