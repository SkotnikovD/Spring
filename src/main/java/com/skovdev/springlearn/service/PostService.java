package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getPosts();

    long createPost(PostDto postDto);
}
