package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.PostDto;
import com.skovdev.springlearn.dto.mapper.PostMapper;
import com.skovdev.springlearn.model.PostWithAuthor;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.PostRepository;
import com.skovdev.springlearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private PostMapper postMapper;
    private UserRepository userRepository;
    private CurrentPrincipalInfoService currentPrincipalInfoService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository, CurrentPrincipalInfoService currentPrincipalInfoService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
        this.currentPrincipalInfoService = currentPrincipalInfoService;
    }

    @Override
    public List<PostWithAuthor> getPosts() {
        return postRepository.getPosts();
    }

    @Override
    public long createPost(PostDto postDto) {
        String currentUserLogin = currentPrincipalInfoService.getCurrentUserLogin();
        Optional<User> user = userRepository.getUser(currentUserLogin);
        return postRepository.createPost(PostMapper.toModel(postDto, user.get().getUserId()));
    }

    @Override
    public boolean deletePost(long id) {
        return postRepository.deletePost(id);
    }

}
