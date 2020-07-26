package com.skovdev.springlearn.repository.jpa;

import com.skovdev.springlearn.model.Post;
import com.skovdev.springlearn.repository.PostRepository;
import com.skovdev.springlearn.repository.jpa.paging.PageRequestByCursorOnId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    public List<Post> getPosts(PageRequestByCursorOnId pageById) {
        int lastId = pageById.getFromId()==null ? Integer.MAX_VALUE : pageById.getFromId(); //Hacky, but works fine. For the sake of simplicity don't want dynamic queries
        return postSpringDataRepository.findAllWithCursorPagination(lastId, PageRequest.of(0, pageById.getPageSize()));
    }


    @Override
    public long createPost(Post post) {
        return postSpringDataRepository.save(post).getPostId();
    }

    @Override
    public void deletePost(int id) {
        postSpringDataRepository.deleteById(id);
    }
}
