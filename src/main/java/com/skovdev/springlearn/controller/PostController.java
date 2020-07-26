package com.skovdev.springlearn.controller;

import com.skovdev.springlearn.dto.CreatePostDto;
import com.skovdev.springlearn.dto.PostWithAuthorDto;
import com.skovdev.springlearn.repository.jpa.paging.PageRequestByCursorOnId;
import com.skovdev.springlearn.service.PostService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @ApiOperation(value = "Get bunch(page) of posts starting (excluding) from specified id. Ids are ordered descending, means newest posts first.")
    @GetMapping()
    public Collection<PostWithAuthorDto> getPosts(
            @ApiParam(value = "Post id from which you want new page to start (this id is excluded from result). The newest posts will be returned, if this parameter is omitted.")
                @RequestParam(name = "lastId", required = false) Integer fromId,
            @ApiParam(value = "Number of posts to return.")
                @RequestParam(name = "pageSize") int pageSize) {
        return postService.getPosts(new PageRequestByCursorOnId(fromId, pageSize));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization bearer token", required = true, dataType = "string", paramType = "header"),
    })
    @PostMapping()
    public long createPost(@RequestBody @Valid CreatePostDto createPostDto) {
        return postService.createPost(createPostDto);
    }

    @DeleteMapping(value = "/{id}")
    @Secured("ADMIN")
    public void deletePost(@PathVariable int id){
        postService.deletePost(id);
    }
}
