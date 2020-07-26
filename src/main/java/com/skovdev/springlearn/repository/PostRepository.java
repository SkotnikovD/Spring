package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.Post;
import com.skovdev.springlearn.repository.jpa.paging.PageRequestByCursorOnId;

import java.util.List;

public interface PostRepository {
    List<Post> getPosts(PageRequestByCursorOnId pageById);

    long createPost(Post post);

    void deletePost(int id);
}
