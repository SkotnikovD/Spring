package com.skovdev.springlearn.controller;

import com.skovdev.springlearn.dto.CreatePostDto;
import com.skovdev.springlearn.dto.PostWithAuthorDto;
import com.skovdev.springlearn.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public long createPost(@RequestBody CreatePostDto createPostDto) {
        return postService.createPost(createPostDto);
    }

    @DeleteMapping(value = "/{id}")
    @Secured("ADMIN")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        boolean res = postService.deletePost(id);
        return res ? new ResponseEntity<>("", HttpStatus.NO_CONTENT) : new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }
}
