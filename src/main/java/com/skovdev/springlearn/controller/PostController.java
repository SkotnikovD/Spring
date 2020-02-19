package com.skovdev.springlearn.controller;

import com.skovdev.springlearn.dto.PostDto;
import com.skovdev.springlearn.service.PostService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Log
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping()
    public List<PostDto> getPosts() {
        //TODO Deal with pagination
        return postService.getPosts();
    }

    @PostMapping()
    public long createPost (@RequestBody PostDto postDto){
        return postService.createPost(postDto);
    }
}