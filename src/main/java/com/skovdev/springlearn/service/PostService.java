package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.CreatePostDto;
import com.skovdev.springlearn.dto.PostWithAuthorDto;

import java.util.Collection;

public interface PostService {

    Collection<PostWithAuthorDto> getPosts();

    long createPost(CreatePostDto createPostDto);

    boolean deletePost(long id);
}
