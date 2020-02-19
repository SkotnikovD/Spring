package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.Post;

import java.util.List;

public interface PostRepository {
    List<Post> getPosts();

    long createPost(Post post);
}
