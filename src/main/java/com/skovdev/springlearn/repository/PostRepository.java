package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.Post;
import com.skovdev.springlearn.model.PostWithAuthor;

import java.util.List;

public interface PostRepository {
    List<PostWithAuthor> getPosts();

    long createPost(Post post);
}
