package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.CreatePostDto;
import com.skovdev.springlearn.dto.PostWithAuthorDto;
import com.skovdev.springlearn.dto.mapper.PostMapper;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.PostRepository;
import com.skovdev.springlearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private CurrentPrincipalInfoService currentPrincipalInfoService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CurrentPrincipalInfoService currentPrincipalInfoService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.currentPrincipalInfoService = currentPrincipalInfoService;
    }

    @Override
    public Collection<PostWithAuthorDto> getPosts() {
        return PostMapper.toDto(postRepository.getPosts());
    }

    @Override
    public long createPost(CreatePostDto createPostDto) {
        String currentUserLogin = currentPrincipalInfoService.getCurrentUserLogin();
        //TODO store user id in token, don't make db querying to get it
        Optional<User> user = userRepository.getUser(currentUserLogin);
        return postRepository.createPost(PostMapper.toModel(createPostDto, user.get().getUserId(), Instant.now().truncatedTo(ChronoUnit.SECONDS)));
    }

    @Override
    public boolean deletePost(long id) {
        return postRepository.deletePost(id);
    }

}
