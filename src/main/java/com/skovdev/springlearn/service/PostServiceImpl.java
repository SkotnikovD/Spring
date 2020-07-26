package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.CreatePostDto;
import com.skovdev.springlearn.dto.PostWithAuthorDto;
import com.skovdev.springlearn.dto.mapper.PostMapper;
import com.skovdev.springlearn.model.Post;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.PostRepository;
import com.skovdev.springlearn.repository.UserRepository;
import com.skovdev.springlearn.repository.jpa.paging.PageRequestByCursorOnId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private CurrentPrincipalInfoService currentPrincipalInfoService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CurrentPrincipalInfoService currentPrincipalInfoService) {
        this.postRepository = postRepository;
        this.currentPrincipalInfoService = currentPrincipalInfoService;
    }

    @Override
    public Collection<PostWithAuthorDto> getPosts(PageRequestByCursorOnId pageById) {
        return PostMapper.toDto(postRepository.getPosts(pageById));
    }

    @Override
    @Transactional
    public long createPost(CreatePostDto createPostDto) {
        //TODO store user id in token, don't make db querying to get it
        //Then we could use .getOne(userId) to eliminate redundant db query for retrieving user
        User currentUser = currentPrincipalInfoService.getCurrentUser();
        Post post = PostMapper.toModel(createPostDto, Instant.now().truncatedTo(ChronoUnit.SECONDS));
        post.setAuthor(currentUser);
        return postRepository.createPost(post);
    }

    @Override
    @Transactional
    public void deletePost(int id) {
        postRepository.deletePost(id);
    }

}
