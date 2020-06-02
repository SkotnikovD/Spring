package com.skovdev.springlearn.controller;

import com.skovdev.springlearn.dto.CreatePostDto;
import com.skovdev.springlearn.dto.PostWithAuthorDto;
import com.skovdev.springlearn.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping()
    public Collection<PostWithAuthorDto> getPosts() {
        return postService.getPosts();
    }

    @PostMapping()
    public long createPost(@RequestBody @Valid CreatePostDto createPostDto) {
        return postService.createPost(createPostDto);
    }

    @DeleteMapping(value = "/{id}")
    @Secured("ADMIN")
    public void deletePost(@PathVariable long id){
        postService.deletePost(id);
    }
}
