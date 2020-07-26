package com.skovdev.springlearn.repository.jpa;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.skovdev.springlearn.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
interface PostSpringDataRepository extends EntityGraphJpaRepository<Post, Integer> {

    @Query("Select p from Post p where p.postId < :fromIdExcluded order by p.postId desc")
    List<Post> findAllWithCursorPagination(@Param("fromIdExcluded") int fromIdExcluded, @NotNull Pageable pageable);

}
