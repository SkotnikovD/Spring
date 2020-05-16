package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.PostDto;
import com.skovdev.springlearn.model.PostWithAuthor;

import java.util.List;

public interface PostService {

    List<PostWithAuthor> getPosts();

    long createPost(PostDto postDto);

    boolean deletePost(long id);
}
