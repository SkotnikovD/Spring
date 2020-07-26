package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.CreatePostDto;
import com.skovdev.springlearn.dto.PostWithAuthorDto;
import com.skovdev.springlearn.repository.jpa.paging.PageRequestByCursorOnId;

import java.util.Collection;

public interface PostService {

    Collection<PostWithAuthorDto> getPosts(PageRequestByCursorOnId pageById);

    long createPost(CreatePostDto createPostDto);

    void deletePost(int id);
}
