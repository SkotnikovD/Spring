package com.skovdev.springlearn.repository.jpa;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.google.common.collect.Lists;
import com.skovdev.springlearn.model.Post;
import com.skovdev.springlearn.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class PostRepositoryJpaImpl implements PostRepository {

    private PostSpringDataRepository postSpringDataRepository;

    @Autowired
    public PostRepositoryJpaImpl(PostSpringDataRepository postSpringDataRepository) {
        this.postSpringDataRepository = postSpringDataRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        EntityGraph entityGraph = EntityGraphUtils.fromAttributePaths("author.roles");
        Sort sortingByCreatedDate = Sort.by(Sort.Direction.DESC, "createdDate");
        Iterable<Post> result = postSpringDataRepository.findAll(sortingByCreatedDate, entityGraph);
        return Lists.newArrayList(result);
    }

    @Override
    public long createPost(Post post) {
        return postSpringDataRepository.save(post).getPostId();
    }

    @Override
    public void deletePost(long id) {
        postSpringDataRepository.deleteById(id);
    }
}
