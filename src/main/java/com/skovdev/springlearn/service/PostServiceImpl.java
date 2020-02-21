package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.PostDto;
import com.skovdev.springlearn.dto.mapper.PostMapper;
import com.skovdev.springlearn.model.Post;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.PostRepository;
import com.skovdev.springlearn.repository.UserRepository;
import com.skovdev.springlearn.security.CurrentPrincipalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<PostDto> getPosts() {
        List<Post> posts = postRepository.getPosts();
        return posts.stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public long createPost(PostDto postDto) {
        String currentUserLogin = currentPrincipalInfoService.getCurrentUserLogin();
        Optional<User> user = userRepository.getUser(currentUserLogin);
        return postRepository.createPost(PostMapper.toModel(postDto, user.get().getUserId()));
    }

}
